package b12app.vyom.com.bookmybus.facerecognize

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Log
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

import java.io.File

import org.opencv.core.CvType;

object FaceUtil {

    private val TAG = "FaceUtil"

    /**
     * 特征保存
     *
     * @param context  Context
     * @param image    Mat
     * @param rect     人脸信息
     * @param fileName 文件名字
     * @return 保存是否成功
     */
    fun saveImage(context: Context, image: Mat, rect: Rect, fileName: String): Boolean {
        // 把检测到的人脸重新定义大小后保存成文件
        val sub = image.submat(rect)
        val mat = Mat()
        val size = Size(100.0, 100.0)
        Imgproc.resize(sub, mat, size)
        return Imgcodecs.imwrite(getFilePath(context, fileName), mat)
    }
    fun matToBitmap(image: Mat) : Bitmap{
        try{
            val bmp = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(image, bmp);
            return bmp
        }catch (e:Exception){ Log.i("matToBitmap ","conver fail")}
       return Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
    }
    fun cutMat(image:Mat, rect:Rect):Mat{
        val subMat = image.submat(rect)
        val size = Size(100.0, 100.0)
        val resizeMat = Mat()
        Imgproc.resize(subMat, resizeMat, size)
        return resizeMat
    }

    /**
     * 删除特征
     *
     * @param context  Context
     * @param fileName 特征文件
     * @return 是否删除成功
     */
    fun deleteImage(context: Context, fileName: String): Boolean {
        // 文件名不能为空
        if (TextUtils.isEmpty(fileName)) {
            return false
        }
        // 文件路径不能为空
        val path = getFilePath(context, fileName)
        if (path != null) {
            val file = File(path)
            return file.exists() && file.delete()
        } else {
            return false
        }
    }

    /**
     * 提取特征
     *
     * @param context  Context
     * @param fileName 文件名
     * @return 特征图片
     */
    fun getImage(context: Context, fileName: String): Bitmap? {
        val filePath = getFilePath(context, fileName)
        return if (TextUtils.isEmpty(filePath)) {
            null
        } else {
            BitmapFactory.decodeFile(filePath)
        }
    }

    /**
     * 特征对比
     *
     * @param context   Context
     * @param fileName1 人脸特征
     * @param fileName2 人脸特征
     * @return 相似度
     */
    fun compare(mat1: Mat, mat2: Mat): Double {
        try {
            val matGrey1=Mat()
            val matGrey2=Mat()
            Imgproc.cvtColor(mat1, matGrey1, Imgproc.COLOR_BGR2GRAY);
            Imgproc.cvtColor(mat2, matGrey2, Imgproc.COLOR_BGR2GRAY);
            return comPareHist(matGrey1, matGrey2);

        }catch (e: Exception) {
            e.printStackTrace()
            return -1.0
        }
    }

    private fun comPareHist(matGrey1: Mat, matGrey2: Mat): Double {
        matGrey1.convertTo(matGrey1,CvType.CV_32F)
        matGrey2.convertTo(matGrey2,CvType.CV_32F)
        return Imgproc.compareHist(matGrey1,matGrey2,Imgproc.CV_COMP_CORREL)
    }

    /**
     * 获取人脸特征路径
     *
     * @param fileName 人脸特征的图片的名字
     * @return 路径
     */
    private fun getFilePath(context: Context, fileName: String): String? {
        return if (TextUtils.isEmpty(fileName)) {
            null
        } else context.applicationContext.filesDir.path + fileName + ".jpg"
        // 内存路径
        // 内存卡路径 需要SD卡读取权限
        // return Environment.getExternalStorageDirectory() + "/FaceDetect/" + fileName + ".jpg";
    }
}
