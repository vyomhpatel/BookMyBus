package b12app.vyom.com.bookmybus.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.util.ArrayList


abstract class PermissionsManager(private val mTargetActivity: Activity) {


    abstract fun authorized(requestCode: Int)

    /**
     * 有权限没有通过
     *
     * @param requestCode      请求码
     * @param lacksPermissions 被拒绝的权限
     */
    abstract fun noAuthorization(requestCode: Int, lacksPermissions: Array<String>)

    /**
     * Android 6.0 以下的系统不校验权限
     *
     *
     * Android 6.0 以下的系统，只要在清单文件中加入了权限，即使在设置中拒绝，checkSelfPermission也会返回已授权！校验没有意义。
     */
    abstract fun ignore()

    /**
     * 检查权限
     *
     * @param requestCode 请求码
     * @param permissions 准备校验的权限
     */
    fun checkPermissions(requestCode: Int, vararg permissions: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0 动态检查权限
            val lacks = ArrayList<String>()
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(mTargetActivity.applicationContext, permission) == PackageManager.PERMISSION_DENIED) {
                    lacks.add(permission)
                }
            }

            if (!lacks.isEmpty()) {
                // 有权限没有授权
                var lacksPermissions = lacks.toTypedArray<String>()
                //申请CAMERA权限
                ActivityCompat.requestPermissions(mTargetActivity, lacksPermissions, requestCode)
            } else {
                // 授权
                authorized(requestCode)
            }
        } else {
            // 6.0 以下版本不校验权限
            ignore()
        }
    }

    /**
     * 复查权限
     *
     *
     * 调用checkPermissions方法后，会提示用户对权限的申请做出选择，选择以后（同意或拒绝）
     * TargetActivity会回调onRequestPermissionsResult方法，
     * 在onRequestPermissionsResult回调方法里，我们调用此方法来复查权限，检查用户的选择是否通过了权限申请
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 授权结果
     */
    fun recheckPermissions(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                // 未授权
                noAuthorization(requestCode, permissions)
                return
            }
        }
        // 授权
        authorized(requestCode)
    }

    companion object {

        private val PACKAGE_URL_SCHEME = "package:"

        /**
         * 进入应用设置
         *
         * @param context context
         */
        fun startAppSettings(context: Context) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.data = Uri.parse(PACKAGE_URL_SCHEME + context.packageName)
            context.startActivity(intent)
        }
    }
}