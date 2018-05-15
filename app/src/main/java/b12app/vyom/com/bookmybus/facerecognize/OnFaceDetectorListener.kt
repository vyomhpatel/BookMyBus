package b12app.vyom.com.bookmybus.facerecognize

import org.opencv.core.Mat
import org.opencv.core.Rect

interface OnFaceDetectorListener {
    abstract fun onFace(mat: Mat, rect: Rect)
}