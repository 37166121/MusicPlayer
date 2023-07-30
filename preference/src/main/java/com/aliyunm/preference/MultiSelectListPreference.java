/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aliyunm.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.TypedArrayUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A {@link Preference} that displays a list of entries as a dialog.
 *
 * <p>This preference saves a set of strings. This set will contain one or more mValues from the
 * {@link #setEntryValues(CharSequence[])} array.
 *
 * @attr name android:entries
 * @attr name android:entryValues
 */
public class MultiSelectListPreference extends DialogPreference {
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private Set<String> mValues = new HashSet<>();

    public MultiSelectListPreference(
            @NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MultiSelectListPreference, defStyleAttr,
                defStyleRes);

        mEntries = a.getTextArray(R.styleable.MultiSelectListPreference_entries);
        mEntryValues = a.getTextArray(R.styleable.MultiSelectListPreference_entryValues);

        a.recycle();
    }

    public MultiSelectListPreference(@NonNull Context context, @Nullable AttributeSet attrs,
                                     int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MultiSelectListPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.dialogPreferenceStyle);
    }

    public MultiSelectListPreference(@NonNull Context context) {
        this(context, null);
    }

    /**
     * Sets the human-readable entries to be shown in the list. This will be shown in subsequent
     * dialogs.
     *
     * <p>Each entry must have a corresponding index in {@link #setEntryValues(CharSequence[])}.
     *
     * @param entries The entries
     * @see #setEntryValues(CharSequence[])
     */
    public void setEntries(CharSequence[] entries) {
        mEntries = entries;
    }

    /**
     * @param entriesResId The entries array as a resource
     * @see #setEntries(CharSequence[])
     */
    public void setEntries(@ArrayRes int entriesResId) {
        setEntries(getContext().getResources().getTextArray(entriesResId));
    }

    /**
     * The list of entries to be shown in the list in subsequent dialogs.
     *
     * @return The list as an array
     */
    public CharSequence[] getEntries() {
        return mEntries;
    }

    /**
     * The array to find the value to save for a preference when an entry from entries is
     * selected. If a user clicks on the second item in entries, the second item in this array
     * will be saved to the preference.
     *
     * @param entryValues The array to be used as mValues to save for the preference
     */
    public void setEntryValues(CharSequence[] entryValues) {
        mEntryValues = entryValues;
    }

    /**
     * @param entryValuesResId The entry mValues array as a resource
     * @see #setEntryValues(CharSequence[])
     */
    public void setEntryValues(@ArrayRes int entryValuesResId) {
        setEntryValues(getContext().getResources().getTextArray(entryValuesResId));
    }

    /**
     * Returns the array of mValues to be saved for the preference.
     *
     * @return The array of mValues
     */
    public CharSequence[] getEntryValues() {
        return mEntryValues;
    }

    /**
     * Sets the values for the key. This should contain entries in {@link #getEntryValues()}.
     *
     * @param values The mValues to set for the key
     */
    public void setValues(Set<String> values) {
        mValues.clear();
        mValues.addAll(values);

        persistStringSet(values);
        notifyChanged();
    }

    /**
     * Retrieves the current values of the key.
     *
     * @return The set of current values
     */
    public Set<String> getValues() {
        return mValues;
    }

    /**
     * Returns the index of the given value (in the entry mValues array).
     *
     * @param value The value whose index should be returned
     * @return The index of the value, or -1 if not found
     */
    public int findIndexOfValue(String value) {
        if (value != null && mEntryValues != null) {
            for (int i = mEntryValues.length - 1; i >= 0; i--) {
                if (TextUtils.equals(mEntryValues[i].toString(), value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    protected boolean[] getSelectedItems() {
        final CharSequence[] entries = mEntryValues;
        final int entryCount = entries.length;
        final Set<String> values = mValues;
        boolean[] result = new boolean[entryCount];

        for (int i = 0; i < entryCount; i++) {
            result[i] = values.contains(entries[i].toString());
        }

        return result;
    }

    @Override
    protected @Nullable Object onGetDefaultValue(@NonNull TypedArray a, int index) {
        final CharSequence[] defaultValues = a.getTextArray(index);
        final Set<String> result = new HashSet<>();

        for (final CharSequence defaultValue : defaultValues) {
            result.add(defaultValue.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onSetInitialValue(Object defaultValue) {
        setValues(getPersistedStringSet((Set<String>) defaultValue));
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            // No need to save instance state
            return superState;
        }

        final SavedState myState = new SavedState(superState);
        myState.mValues = getValues();
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Parcelable state) {
        if (state == null || !state.getClass().equals(SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        setValues(myState.mValues);
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };

        Set<String> mValues;

        SavedState(Parcel source) {
            super(source);
            final int size = source.readInt();
            mValues = new HashSet<>();
            String[] strings = new String[size];
            source.readStringArray(strings);

            Collections.addAll(mValues, strings);
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mValues.size());
            dest.writeStringArray(mValues.toArray(new String[mValues.size()]));
        }
    }
}
