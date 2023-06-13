package com.dailycloud.dailycloud.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.dailycloud.dailycloud.data.remote.service.CameraService
import com.dailycloud.dailycloud.util.MLHelper
import com.dailycloud.dailycloud.util.Util.createImageFile
import java.io.File
import javax.inject.Inject

class CameraRepository @Inject constructor(
    private val cameraProvider: ProcessCameraProvider,
    private val cameraSelector: CameraSelector,
    private val cameraPreview: Preview,
    private val imageCapture: ImageCapture,
) : CameraService {

    override suspend fun showCameraPreview(previewView: PreviewView, lifecycleOwner: LifecycleOwner) {

        cameraPreview.setSurfaceProvider(previewView.surfaceProvider)
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                cameraPreview,
                imageCapture
            )
        } catch (e: Exception) {
            //
        }
    }

    override suspend fun takePicture(context: Context, upload: (File, String) -> Unit) {
        val file = context.createImageFile()
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                var bitmap = BitmapFactory.decodeFile(file.path)
                val dimension = minOf(bitmap.width, bitmap.height)
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension)
                bitmap = Bitmap.createScaledBitmap(bitmap, 48, 48, false)
                val classifier = MLHelper(context)
                classifier.classifyImage(bitmap)
                classifier.result?.let {
                    upload(file, it)
                }
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraRepository", "onError: ${exception.message}", exception)
            }
        })
    }

}