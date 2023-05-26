package com.dailycloud.dailycloud.ui.screen.camera

import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.CameraRepository
import com.dailycloud.dailycloud.data.DailyCloudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(private val repository: CameraRepository) : ViewModel() {

    fun showCameraPreview(previewView: PreviewView, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            repository.showCameraPreview(
                previewView,
                lifecycleOwner
            )
        }
    }
}