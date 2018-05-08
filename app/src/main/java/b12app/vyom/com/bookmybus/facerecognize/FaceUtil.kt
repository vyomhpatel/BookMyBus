package b12app.vyom.com.bookmybus.facerecognize

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.CV_COMP_CORREL
import org.opencv.imgproc.Imgproc.CV_COMP_INTERSECT
import java.io.File

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
        // 原图置灰
        val grayMat = Mat()
        Imgproc.cvtColor(image, grayMat, Imgproc.COLOR_BGR2GRAY)
        // 把检测到的人脸重新定义大小后保存成文件
        val sub = grayMat.submat(rect)
        val mat = Mat()
        val size = Size(100.0, 100.0)
        Imgproc.resize(sub, mat, size)
        return Imgcodecs.imwrite(getFilePath(context, fileName), mat)
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
    fun compare(context: Context, fileName1: String, fileName2: String): Double {
//        try {
//            val pathFile1 = getFilePath(context, fileName1)
//            val pathFile2 = getFilePath(context, fileName2)
//            val image1 = cvLoadImage(pathFile1, CV_LOAD_IMAGE_GRAYSCALE)
//            val image2 = cvLoadImage(pathFile2, CV_LOAD_IMAGE_GRAYSCALE)
//            if (null == image1 || null == image2) {
//                return -1.0
//            }
//
//            val l_bins = 256
//            val hist_size = intArrayOf(l_bins)
//            val v_ranges = floatArrayOf(0f, 255f)
//            val ranges = arrayOf(v_ranges)
//
//            val imageArr1 = arrayOf<Mat>(image1)
//            val imageArr2 = arrayOf<Mat>(image2)
//            val Histogram1 = CvHistogram.create(1, hist_size, CV_HIST_ARRAY, ranges, 1)
//            val Histogram2 = CvHistogram.create(1, hist_size, CV_HIST_ARRAY, ranges, 1)
//            cvCalcHist(imageArr1, Histogram1, 0, null)
//            cvCalcHist(imageArr2, Histogram2, 0, null)
//            cvNormalizeHist(Histogram1, 100.0)
//            cvNormalizeHist(Histogram2, 100.0)
//            // 参考：http://blog.csdn.net/nicebooks/article/details/8175002
//            val c1 = cvCompareHist(Histogram1, Histogram2, CV_COMP_CORREL) * 100
//            val c2 = cvCompareHist(Histogram1, Histogram2, CV_COMP_INTERSECT)
//            //            Log.i(TAG, "compare: ----------------------------");
//            //            Log.i(TAG, "compare: c1 = " + c1);
//            //            Log.i(TAG, "compare: c2 = " + c2);
//            //            Log.i(TAG, "compare: 平均值 = " + ((c1 + c2) / 2));
//            //            Log.i(TAG, "compare: ----------------------------");
//            return (c1 + c2) / 2
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return -1.0
//        }
        return 0.0
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
