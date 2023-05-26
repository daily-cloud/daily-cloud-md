package com.dailycloud.dailycloud.data

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.dailycloud.dailycloud.data.remote.service.CameraService
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

    override suspend fun takePicture(context: Context) {
        //
    }

}