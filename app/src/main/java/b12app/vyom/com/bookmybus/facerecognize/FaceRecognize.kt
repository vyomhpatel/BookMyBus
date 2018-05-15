package b12app.vyom.com.bookmybus.facerecognize

import android.content.Context
import android.hardware.Camera
import android.util.AttributeSet
import android.util.Log
import b12app.vyom.com.bookmybus.R
import org.opencv.android.*
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream
import java.io.IOException



class FaceRecognize(context: Context?, attrs: AttributeSet?) : JavaCameraView(context, attrs), CameraBridgeViewBase.CvCameraViewListener2 {
    var mCameraSwitchCount = 0
    var mRgba: Mat? = null
    var mGray: Mat? = null
    val TAG = "FaceRecognize"
    var mJavaDetector: CascadeClassifier? = null
    var mOnFaceDetectorListener: OnFaceDetectorListener? = null
    private var mAbsoluteFaceSize = 0
    private val FACE_RECT_COLOR = Scalar(0.0, 255.0, 0.0, 255.0)

    // 脸部占屏幕多大面积的时候开始识别
    private val RELATIVE_FACE_SIZE = 0.2f


    fun loadOpenCV(context: Context): Boolean {

        val isLoaded = OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, context, mLoaderCallback)
        if (isLoaded) {
            setCvCameraViewListener(this)
        }
        return isLoaded
    }

    private var isLoadSuccess = false
    private val mLoaderCallback = object : BaseLoaderCallback(context) {

        override fun onManagerConnected(status: Int) {
            when (status) {
                LoaderCallbackInterface.SUCCESS -> {
                    Log.i(TAG, "onManagerConnected: OpenCV load success")
                    isLoadSuccess = true
                    try {
                        val inputStream = resources.openRawResource(R.raw.lbpcascade_frontalface)
                        val cascadeDir = context?.getDir("cascade", Context.MODE_PRIVATE)
                        val cascadeFile = File(cascadeDir, "lbpcascade_frontalface.xml")
                        val outputStream = FileOutputStream(cascadeFile)

                        val buffer = ByteArray(4096)
                        var bytesRead: Int
                        while (true) {
                            bytesRead = inputStream.read(buffer)
                            if(bytesRead==-1) break
                            outputStream.write(buffer, 0, bytesRead)
                        }
                        inputStream.close()
                        outputStream.close()
                        mJavaDetector = CascadeClassifier(cascadeFile.getAbsolutePath())
                        mJavaDetector!!.load(cascadeFile.getAbsolutePath());

                        if (mJavaDetector!!.empty()) {
                            Log.e(TAG, "级联分类器加载失败")
                            mJavaDetector = null
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.e(TAG, "没有找到级联分类器")
                    }
                    enableView()
                }
                LoaderCallbackInterface.MARKET_ERROR // OpenCV loader can not start Google Play Market.
                -> {
                    Log.i(TAG, "onManagerConnected: 打开Google Play失败")
                }
                LoaderCallbackInterface.INSTALL_CANCELED // Package installation has been canceled.
                -> {
                    Log.i(TAG, "onManagerConnected: 安装被取消")
                }
                LoaderCallbackInterface.INCOMPATIBLE_MANAGER_VERSION // Application is incompatible with this version of OpenCV Manager. Possibly, a service update is required.
                -> {
                    Log.i(TAG, "onManagerConnected: 版本不正确")
                }
                else // Other status,
                -> {
                    Log.i(TAG, "onManagerConnected: 其他错误")
                }
            }
        }
    }

    override fun enableView() {
        if (isLoadSuccess) {
            super.enableView()
        }
    }

    override fun disableView() {
        if (isLoadSuccess) {
            super.disableView()
        }
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        mGray = Mat()
        mRgba = Mat()
    }

    override fun onCameraViewStopped() {
        mGray?.release()
        mRgba?.release()
    }

    override fun onCameraFrame(inputFrame: CvCameraViewFrame?): Mat {
        mRgba = inputFrame!!.rgba()
        mGray = inputFrame.gray()

        if (mAbsoluteFaceSize == 0) {
            val height = mGray!!.rows()
            if (Math.round(height * RELATIVE_FACE_SIZE) > 0) {
                mAbsoluteFaceSize = Math.round(height * RELATIVE_FACE_SIZE)
            }
        }

        if (mJavaDetector != null) {
            val faces = MatOfRect()
            mJavaDetector!!.detectMultiScale(mGray, // 要检查的灰度图像
                    faces, // 检测到的人脸
                    1.1, // 表示在前后两次相继的扫描中，搜索窗口的比例系数。默认为1.1即每次搜索窗口依次扩大10%;
                    10, // 默认是3 控制误检测，表示默认几次重叠检测到人脸，才认为人脸存在
                    4, //CV_HAAR_DO_CANNY_PRUNING ,// CV_HAAR_SCALE_IMAGE, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                    Size(mAbsoluteFaceSize.toDouble(), mAbsoluteFaceSize.toDouble()),
                    Size(mGray!!.width().toDouble(), mGray!!.height().toDouble()))

            // 检测到人脸
            val facesArray = faces.toArray()
            for (aFacesArray in facesArray) {
                Imgproc.rectangle(mRgba, aFacesArray.tl(), aFacesArray.br(), FACE_RECT_COLOR, 3)
                if (null != mOnFaceDetectorListener) {
                    mOnFaceDetectorListener!!.onFace(mRgba!!, aFacesArray)
                }
            }
        }
        return mRgba!!
    }

    fun switchCamera(): Boolean {
        // 摄像头总数
        var numberOfCameras = 0
        numberOfCameras = Camera.getNumberOfCameras()
        // 2个及以上摄像头
        if (1 < numberOfCameras) {
            // 设备没有摄像头
            val index = ++mCameraSwitchCount % numberOfCameras
            disableView()
            setCameraIndex(index)
            enableView()
            return true
        }
        return false
    }

    fun setOnFaceDetectorListener(listener: OnFaceDetectorListener) {
        mOnFaceDetectorListener = listener
    }


}

