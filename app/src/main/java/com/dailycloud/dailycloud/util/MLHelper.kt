package com.dailycloud.dailycloud.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.dailycloud.dailycloud.ml.ModelCv
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class MLHelper(val context: Context) {

    var result: String? = null

    fun classifyImage(bitmap: Bitmap) {
        val model = ModelCv.newInstance(context)

        val inputFeature = TensorBuffer.createFixedSize(intArrayOf(1, 48, 48, 1), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * 48 * 48 * 1)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(48 * 48)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        var pixel = 0
        for (i in 0 until 48) {
            for (j in 0 until 48) {
                val value = intValues[pixel++]
                byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 255f))
//                byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 255f))
//                byteBuffer.putFloat((value and 0xFF) * (1f / 255f))
            }
        }

        inputFeature.loadBuffer(byteBuffer)

        val outputs = model.process(inputFeature)
        val outputFeature = outputs.outputFeature0AsTensorBuffer

        result = if (outputFeature.floatArray.toList().indexOf(outputFeature.floatArray.maxOrNull()) == 0) "happy" else "sad"
        Log.d("MLHelper", "outputFeature: ${outputFeature.floatArray.toList()}")

        model.close()
    }
}