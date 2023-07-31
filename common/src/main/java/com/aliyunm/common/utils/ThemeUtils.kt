package com.aliyunm.common.utils

import android.content.res.Configuration
import androidx.activity.ComponentActivity

object ThemeUtils {
    fun changeTheme(activity : ComponentActivity) {
        val configuration = Configuration()
        configuration.uiMode = if (configuration.uiMode == Configuration.UI_MODE_NIGHT_YES) {
            Configuration.UI_MODE_NIGHT_NO
        } else {
            Configuration.UI_MODE_NIGHT_YES
        }
        activity.onConfigurationChanged(configuration)
    }
}