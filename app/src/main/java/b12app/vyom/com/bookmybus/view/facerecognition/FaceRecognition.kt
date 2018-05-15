package b12app.vyom.com.bookmybus.view.facerecognition

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import b12app.vyom.com.bookmybus.R
import android.graphics.Bitmap
import kotlinx.android.synthetic.main.activity_face_recognition.*
import android.Manifest;
import android.content.Intent
import android.util.Log
import android.widget.Toast
import b12app.vyom.com.bookmybus.facerecognize.FaceUtil
import b12app.vyom.com.bookmybus.facerecognize.OnFaceDetectorListener
import b12app.vyom.com.bookmybus.utils.PermissionsManager
import b12app.vyom.com.bookmybus.view.home.HomeActivity
import org.opencv.android.Utils


import org.opencv.core.Mat
import org.opencv.core.Rect
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.RejectedExecutionException

class FaceRecognition : AppCompatActivity(), OnFaceDetectorListener {
    private val TAG = "similarity ";
    private val FACE = "face"
    private var isGettingFace = 0
    private var mBitmapFace: Bitmap? = null
    private var faceOriginal: Mat? = null
    private var cmp = 0.0
    private var pool:ExecutorService?=null
    private var mPermissionsManager: PermissionsManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_recognition)

        faceRecognition!!.setOnFaceDetectorListener(this)
        faceRecognition!!.loadOpenCV(this)
        mBitmapFace = FaceUtil.getImage(this, FACE)
        face1.setImageBitmap(mBitmapFace)
        //sign up

        pool = Executors.newFixedThreadPool(2)
        face1.setOnClickListener {
            isGettingFace = 1
        }


        // 切换摄像头（如果有多个）
        switch_camera.setOnClickListener {
            // 切换摄像头
            val isSwitched = faceRecognition!!.switchCamera()
            Toast.makeText(applicationContext, if (isSwitched) "摄像头切换成功" else "摄像头切换失败", Toast.LENGTH_SHORT).show()
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
     * 检测到人脸
     *
     * @param mat  Mat
     * @param rect Rect
     */
    override fun onFace(mat: Mat, rect: Rect) {
        if (isGettingFace != 0) {
            isGettingFace = 0
            FaceUtil.saveImage(this, mat, rect, FACE)
            faceOriginal = FaceUtil.cutMat(mat, rect)
            val bitmap = FaceUtil.matToBitmap(FaceUtil.cutMat(mat, rect))
            runOnUiThread {
                face1?.setImageBitmap(bitmap)
            }
        }
        if (faceOriginal == null&&mBitmapFace!=null) {
            faceOriginal = Mat()
            Utils.bitmapToMat(mBitmapFace, faceOriginal);
        }
        if(faceOriginal!=null){
            val compareThread = Thread(object:Runnable{
                override fun run() {
                    cmp = FaceUtil.compare(faceOriginal!!, FaceUtil.cutMat(mat, rect))
                    if (cmp >= 0.72) {
                        pool?.shutdown();
                        gotoMainActivity()
                        finish()
                    }
                }
            })
            try{
                pool?.execute(compareThread)
            }catch (e: RejectedExecutionException){
                Log.e(TAG,"already matched")
            }

        }

    }

    private fun gotoMainActivity() {
        startActivity(Intent(this@FaceRecognition, HomeActivity::class.java))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPermissionsManager?.recheckPermissions(requestCode, permissions, grantResults)
    }
}