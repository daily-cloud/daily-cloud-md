package com.dailycloud.dailycloud.data.remote.service

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import java.io.File

interface CameraService {

    suspend fun showCameraPreview(previewView: PreviewView, lifecycleOwner: LifecycleOwner)
    suspend fun takePicture(context: Context, upload: (File, String) -> Unit)

}