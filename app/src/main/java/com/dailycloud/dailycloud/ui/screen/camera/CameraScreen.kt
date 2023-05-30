package com.dailycloud.dailycloud.ui.screen.camera

import android.Manifest
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrowseGallery
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme
import com.dailycloud.dailycloud.ui.theme.Primary
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel = hiltViewModel(),
    toResult: () -> Unit,
) {

    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    if (!permissionState.status.isGranted){
        SideEffect {
            permissionState.launchPermissionRequest()
        }
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val screeHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var previewView:PreviewView

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = {
                previewView = PreviewView(it)
                viewModel.showCameraPreview(previewView, lifecycleOwner)
                previewView
            },
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.BrowseGallery,
                    contentDescription = "Gallery",
                    tint = Color.Transparent
                )
            }
            IconButton(onClick = { toResult() }) {
                Icon(
                    imageVector = Icons.Filled.Camera,
                    contentDescription = "Camera",
                    tint = Color.White,
                    modifier = Modifier.size(128.dp)
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Cameraswitch,
                    contentDescription = "Switch Camera",
                    tint = Color.White
                )
            }
        }
    }
}