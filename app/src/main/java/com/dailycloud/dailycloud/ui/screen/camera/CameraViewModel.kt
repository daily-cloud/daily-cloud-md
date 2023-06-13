package com.dailycloud.dailycloud.ui.screen.camera

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.CameraRepository
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.util.Util.reduceFileImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(private val repository: CameraRepository, private val repo: DailyCloudRepository) : ViewModel() {

    fun showCameraPreview(previewView: PreviewView, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            repository.showCameraPreview(
                previewView,
                lifecycleOwner
            )
        }
    }

    fun takePhoto(context: Context, toJournal: (String) -> Unit) {
        viewModelScope.launch {
            repository.takePicture(context) { file, result ->
                val compressFile = reduceFileImage(file)
                val requestImageFile = compressFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    compressFile.name,
                    requestImageFile
                )
                toJournal(result)
//                Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
//                viewModelScope.launch {
//                    repo.addJournal("", "", result, "")
//                    repo.uploadImage(imageMultipart).collect {
//                        when (it) {
//                            is UiState.Loading -> {  }
//                            is UiState.Success -> { toResult() }
//                            is UiState.Error -> { Log.e("UploadImage: ", it.errorMessage) }
//                        }
//                    }
//                }
            }
        }
    }
}