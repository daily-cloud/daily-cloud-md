package com.dailycloud.dailycloud.data.remote.service

import android.app.Application
import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.dailycloud.dailycloud.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import java.io.File

interface CameraService {

    suspend fun showCameraPreview(previewView: PreviewView, lifecycleOwner: LifecycleOwner)
    suspend fun takePicture(context: Context, upload: (File, String) -> Unit)

}