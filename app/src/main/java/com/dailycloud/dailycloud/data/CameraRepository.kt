package com.dailycloud.dailycloud.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.dailycloud.dailycloud.data.remote.service.ApiService
import com.dailycloud.dailycloud.data.remote.service.CameraService
import com.dailycloud.dailycloud.ui.common.UiState
import com.dailycloud.dailycloud.util.Util
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.nio.file.Files.createFile
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
                cameraPreview
            )
        } catch (e: Exception) {
            //
        }
    }

    override suspend fun takePicture(context: Context, application: Application, upload: (File) -> Unit) {
        val file = Util.createFile(application)
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                upload(file)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraRepository", "onError: ${exception.message}", exception)
            }
        })
    }

}