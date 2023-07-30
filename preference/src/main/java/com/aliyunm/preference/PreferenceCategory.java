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
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.TypedArrayUtils;

/**
 * A container that is used to group similar {@link Preference}s. A PreferenceCategory displays a
 * category title and visually separates groups of Preferences.
 *
 * <div class="special reference">
 * <h3>Developer Guides</h3>
 * <p>For information about building a settings screen using the AndroidX Preference library, see
 * <a href="{@docRoot}guide/topics/ui/settings.html">Settings</a>.</p>
 * </div>
 */
public class PreferenceCategory extends PreferenceGroup {

    public PreferenceCategory(
            @NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PreferenceCategory(@NonNull Context context, @Nullable AttributeSet attrs,
            int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PreferenceCategory(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.preferenceCategoryStyle);
    }

    public PreferenceCategory(@NonNull Context context) {
        this(context, null);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean shouldDisableDependents() {
        return !super.isEnabled();
    }

    @Override
    public void onBindViewHolder(@NonNull PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        if (Build.VERSION.SDK_INT >= VERSION_CODES.P) {
            holder.itemView.setAccessibilityHeading(true);
        }
    }
}
