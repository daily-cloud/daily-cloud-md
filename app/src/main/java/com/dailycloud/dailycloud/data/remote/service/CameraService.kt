package com.dailycloud.dailycloud.data.remote.service

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner

interface CameraService {

    suspend fun showCameraPreview(previewView: PreviewView, lifecycleOwner: LifecycleOwner)
    suspend fun takePicture(context: Context)

}