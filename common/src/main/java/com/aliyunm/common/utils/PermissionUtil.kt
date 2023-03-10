package com.example.localidentification.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object PermissionUtil {

    const val PERMISSION_CODE = 0x2002

    fun checkSelfPermission(context : Context, permission : String): Boolean {
        val p = ActivityCompat.checkSelfPermission(context, permission)
        if (p == PackageManager.PERMISSION_GRANTED) {
            return true
        } else if (p == PackageManager.PERMISSION_DENIED) {
            return false
        }
        return false
    }

    /**
     * 注册权限
     */
    fun requestPermission(fragment: Fragment, name : String, isGranted : () -> Unit = {}, denied : () -> Unit = {}) {
        requestPermission(fragment.requireActivity(), name, isGranted, denied)
    }
    /**
     * 注册权限
     */
    fun requestPermission(activity: FragmentActivity, name : String, isGranted : () -> Unit = {}, denied : () -> Unit = {}) {
        when(Build.VERSION.SDK_INT) {
            // 不同版本处理情况
        }
        if (checkSelfPermission(activity.applicationContext, name)) {
            // 拥有权限
            isGranted()
        } else {
            val requestPermissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    // 权限已获取
                    isGranted()
                } else {
                    // 权限被拒绝
                    denied()
                }
            }
            requestPermissionLauncher.launch(name)
        }
    }

    /**
     * 注册权限组
     */
    fun requestPermissions(activity: FragmentActivity, names : Array<String>, PERMISSION_CODE : Int = this.PERMISSION_CODE, isGranted : () -> Unit = {}, denied : () -> Unit = {}) {
        var granted = true
        names.forEach {
            granted = granted && ActivityCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
        }
        if (!granted) {
            // 获取权限
            activity.requestPermissions(names, PERMISSION_CODE)
            denied()
        } else {
            isGranted()
        }
    }

    /**
     * 注册必须进入设置页面的权限
     */
    fun requestSettingPermissions(fragment: Fragment, action : String, PERMISSION_CODE : Int) {
        requestSettingPermissions(fragment.requireActivity(), action, PERMISSION_CODE)
    }

    /**
     * 注册必须进入设置页面的权限
     */
    fun requestSettingPermissions(activity: FragmentActivity, action : String, PERMISSION_CODE : Int) {
        val intent = Intent()
        intent.action = action
        activity.startActivityForResult(intent, PERMISSION_CODE)
    }
}