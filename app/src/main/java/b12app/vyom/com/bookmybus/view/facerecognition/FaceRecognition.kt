package b12app.vyom.com.bookmybus.view.facerecognition

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import b12app.vyom.com.bookmybus.R
import android.graphics.Bitmap
import kotlinx.android.synthetic.main.activity_face_recognition.*
import android.Manifest;
import android.content.Intent
import android.util.Log;
import android.view.View;
import android.widget.Toast
import b12app.vyom.com.bookmybus.facerecognize.FaceUtil
import b12app.vyom.com.bookmybus.facerecognize.OnFaceDetectorListener
import b12app.vyom.com.bookmybus.utils.PermissionsManager
import b12app.vyom.com.bookmybus.view.home.HomeActivity

import org.opencv.core.Mat
import org.opencv.core.Rect

class FaceRecognition : AppCompatActivity(), OnFaceDetectorListener {
    private val TAG = "FaceRecognition";

    private val FACE1 = "face1"
    private val FACE2 = "face2"
    private var isGettingFace = false
    private var mBitmapFace1: Bitmap? = null
    private var mBitmapFace2: Bitmap? = null
    private var cmp: Double = 0.toDouble()
    private var mPermissionsManager:PermissionsManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_recognition)

        faceRecognition!!.setOnFaceDetectorListener(this)
        faceRecognition!!.loadOpenCV(this)

        // 抓取一张人脸
        bn_get_face.setOnClickListener { isGettingFace = true }
        // 切换摄像头（如果有多个）
        switch_camera.setOnClickListener {
            // 切换摄像头
            val isSwitched = faceRecognition!!.switchCamera()
            Toast.makeText(applicationContext, if (isSwitched) "摄像头切换成功" else "摄像头切换失败", Toast.LENGTH_SHORT).show()
        }
        gotoMain.setOnClickListener{
            startActivity(Intent(this,HomeActivity::class.java))
        }
        // 动态权限检查器
        mPermissionsManager = object : PermissionsManager(this@FaceRecognition) {
            override fun authorized(requestCode: Int) {
                Toast.makeText(applicationContext, "权限通过！", Toast.LENGTH_SHORT).show()
            }

            override fun noAuthorization(requestCode: Int, lacksPermissions: Array<String>) {
                val builder = android.support.v7.app.AlertDialog.Builder(this@FaceRecognition)
                builder.setTitle("提示")
                builder.setMessage("缺少相机权限！")
                builder.setPositiveButton("设置权限") { dialog, which -> PermissionsManager.startAppSettings(applicationContext) }
                builder.create().show()
            }

            override fun ignore() {
                val builder = android.support.v7.app.AlertDialog.Builder(this@FaceRecognition)
                builder.setTitle("提示")
                builder.setMessage("Android 6.0 以下系统不做权限的动态检查\n如果运行异常\n请优先检查是否安装了 OpenCV Manager\n并且打开了 CAMERA 权限")
                builder.setPositiveButton("确认", null)
                builder.setNeutralButton("设置权限") { dialog, which -> PermissionsManager.startAppSettings(applicationContext) }
                builder.create().show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // 检查权限
        mPermissionsManager?.checkPermissions(0, Manifest.permission.CAMERA)
    }

    /**
     * 设置应用权限
     *
     * @param view view
     */
    fun setPermissions(view: View) {
        PermissionsManager.startAppSettings(applicationContext)
    }

    /**
     * 检测到人脸
     *
     * @param mat  Mat
     * @param rect Rect
     */
    override fun onFace(mat: Mat, rect: Rect) {
        if (isGettingFace) {
            if (null == mBitmapFace1 || null != mBitmapFace2) {
                mBitmapFace1 = null
                mBitmapFace2 = null
                // 保存人脸信息并显示
                FaceUtil.saveImage(this, mat, rect, FACE1)
                mBitmapFace1 = FaceUtil.getImage(this, FACE1)
                cmp = 0.0
            } else {
                FaceUtil.saveImage(this, mat, rect, FACE2)
                mBitmapFace2 = FaceUtil.getImage(this, FACE2)

                // 计算相似度
                cmp = FaceUtil.compare(this, FACE1, FACE2)
                Log.i(TAG, "onFace: 相似度 : $cmp")
            }
            runOnUiThread {
                if (null == mBitmapFace1) {
                    face1?.setImageResource(R.mipmap.ic_contact_picture)
                } else {
                    face1?.setImageBitmap(mBitmapFace1)
                }
                if (null == mBitmapFace2) {
                    face2?.setImageResource(R.mipmap.ic_contact_picture)
                } else {
                    face2?.setImageBitmap(mBitmapFace2)
                }
                samilarity?.setText(String.format("samilarity :  %.2f", cmp) + "%")
            }

            isGettingFace = false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPermissionsManager?.recheckPermissions(requestCode, permissions, grantResults)
    }
}