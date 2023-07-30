/*
 * Copyright 2018 The Android Open Source Project
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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

/**
 * A base class for {@link Preference}s that are dialog-based. When clicked, these
 * preferences will open a dialog showing the actual preference controls.
 *
 * @attr name android:dialogTitle
 * @attr name android:dialogMessage
 * @attr name android:dialogIcon
 * @attr name android:dialogLayout
 * @attr name android:positiveButtonText
 * @attr name android:negativeButtonText
 */
public abstract class DialogPreference extends Preference {

    private CharSequence mDialogTitle;
    private CharSequence mDialogMessage;
    private Drawable mDialogIcon;
    private CharSequence mPositiveButtonText;
    private CharSequence mNegativeButtonText;
    private int mDialogLayoutResId;

    public DialogPreference(
            @NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.DialogPreference, defStyleAttr, defStyleRes);

        mDialogTitle = a.getString(R.styleable.DialogPreference_dialogTitle);
        if (mDialogTitle == null) {
            // Fall back on the regular title of the preference
            // (the one that is seen in the list)
            mDialogTitle = getTitle();
        }

        mDialogMessage = a.getString(R.styleable.DialogPreference_dialogMessage);
        mDialogIcon = a.getDrawable(R.styleable.DialogPreference_dialogIcon);
        mPositiveButtonText = a.getString(R.styleable.DialogPreference_positiveButtonText);
        mNegativeButtonText = a.getString(R.styleable.DialogPreference_negativeButtonText);
        mDialogLayoutResId = a.getResourceId(R.styleable.DialogPreference_dialogLayout, 0);

        a.recycle();
    }

    public DialogPreference(@NonNull Context context, @Nullable AttributeSet attrs,
                            int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DialogPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.dialogPreferenceStyle);
    }

    public DialogPreference(@NonNull Context context) {
        this(context, null);
    }

    /**
     * Sets the title of the dialog. This will be shown on subsequent dialogs.
     *
     * @param dialogTitle The title
     */
    public void setDialogTitle(@Nullable CharSequence dialogTitle) {
        mDialogTitle = dialogTitle;
    }

    /**
     * @param dialogTitleResId The dialog title as a resource
     * @see #setDialogTitle(CharSequence)
     */
    public void setDialogTitle(int dialogTitleResId) {
        setDialogTitle(getContext().getString(dialogTitleResId));
    }

    /**
     * Returns the title to be shown on subsequent dialogs.
     *
     * @return The title
     */
    @Nullable
    public CharSequence getDialogTitle() {
        return mDialogTitle;
    }

    /**
     * Sets the message of the dialog. This will be shown on subsequent dialogs.
     *
     * <p>This message forms the content view of the dialog and conflicts with list-based
     * dialogs, for example. If setting a custom {@link View} on a dialog via
     * {@link #setDialogLayoutResource(int)}, include a {@link android.widget.TextView} with ID
     * {@link android.R.id#message} and it will be populated with this message.
     *
     * @param dialogMessage The message
     */
    public void setDialogMessage(@Nullable CharSequence dialogMessage) {
        mDialogMessage = dialogMessage;
    }

    /**
     * @param dialogMessageResId The dialog message as a resource
     * @see #setDialogMessage(CharSequence)
     */
    public void setDialogMessage(int dialogMessageResId) {
        setDialogMessage(getContext().getString(dialogMessageResId));
    }

    /**
     * Returns the message to be shown on subsequent dialogs.
     *
     * @return The message
     */
    @Nullable
    public CharSequence getDialogMessage() {
        return mDialogMessage;
    }

    /**
     * Sets the icon of the dialog. This will be shown on subsequent dialogs.
     *
     * @param dialogIcon The icon, as a {@link Drawable}
     */
    public void setDialogIcon(@Nullable Drawable dialogIcon) {
        mDialogIcon = dialogIcon;
    }

    /**
     * Sets the icon (resource ID) of the dialog. This will be shown on subsequent dialogs.
     *
     * @param dialogIconRes The icon, as a resource ID
     */
    public void setDialogIcon(int dialogIconRes) {
        mDialogIcon = AppCompatResources.getDrawable(getContext(), dialogIconRes);
    }

    /**
     * Returns the icon to be shown on subsequent dialogs.
     *
     * @return The icon, as a {@link Drawable}
     */
    @Nullable
    public Drawable getDialogIcon() {
        return mDialogIcon;
    }

    /**
     * Sets the text of the positive button of the dialog. This will be shown on subsequent dialogs.
     *
     * @param positiveButtonText The text of the positive button
     */
    public void setPositiveButtonText(@Nullable CharSequence positiveButtonText) {
        mPositiveButtonText = positiveButtonText;
    }

    /**
     * @param positiveButtonTextResId The positive button text as a resource
     * @see #setPositiveButtonText(CharSequence)
     */
    public void setPositiveButtonText(int positiveButtonTextResId) {
        setPositiveButtonText(getContext().getString(positiveButtonTextResId));
    }

    /**
     * Returns the text of the positive button to be shown on subsequent dialogs.
     *
     * @return The text of the positive button
     */
    @Nullable
    public CharSequence getPositiveButtonText() {
        return mPositiveButtonText;
    }

    /**
     * Sets the text of the negative button of the dialog. This will be shown on subsequent dialogs.
     *
     * @param negativeButtonText The text of the negative button
     */
    public void setNegativeButtonText(@Nullable CharSequence negativeButtonText) {
        mNegativeButtonText = negativeButtonText;
    }

    /**
     * @param negativeButtonTextResId The negative button text as a resource
     * @see #setNegativeButtonText(CharSequence)
     */
    public void setNegativeButtonText(int negativeButtonTextResId) {
        setNegativeButtonText(getContext().getString(negativeButtonTextResId));
    }

    /**
     * Returns the text of the negative button to be shown on subsequent dialogs.
     *
     * @return The text of the negative button
     */
    @Nullable
    public CharSequence getNegativeButtonText() {
        return mNegativeButtonText;
    }

    /**
     * Sets the layout resource that is inflated as the {@link View} to be shown as the content
     * view of subsequent dialogs.
     *
     * @param dialogLayoutResId The layout resource ID to be inflated
     * @see #setDialogMessage(CharSequence)
     */
    public void setDialogLayoutResource(int dialogLayoutResId) {
        mDialogLayoutResId = dialogLayoutResId;
    }

    /**
     * Returns the layout resource that is used as the content view for subsequent dialogs.
     *
     * @return The layout resource
     */
    public int getDialogLayoutResource() {
        return mDialogLayoutResId;
    }

    @Override
    protected void onClick() {
        getPreferenceManager().showDialog(this);
    }

    /**
     * Interface for {@link PreferenceFragmentCompat}s to implement to allow
     * {@link DialogPreference}s to find the preference that launched the dialog.
     */
    public interface TargetFragment {
        /**
         * Finds a {@link Preference} with the given key. Returns {@code null} if no
         * {@link Preference} could be found with the given key.
         *
         * @param key The key of the {@link Preference} to retrieve
         * @return The {@link Preference} with the key, or {@code null}
         * @see PreferenceGroup#findPreference(CharSequence)
         */
        @SuppressWarnings("TypeParameterUnusedInFormals")
        @Nullable
        <T extends Preference> T findPreference(@NonNull CharSequence key);
    }
}
