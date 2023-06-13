package com.dailycloud.dailycloud.ui.screen.profile


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.components.CustomOutlinedButton
import com.dailycloud.dailycloud.ui.theme.Primary
import com.dailycloud.dailycloud.util.Util.createImageFile
import java.util.Objects

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    backToProfile: () -> Unit,
    name: String?
) {
    val nameEdit = remember { mutableStateOf(name) }

    var profileImageUrl = "https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
    val showDialog = remember { mutableStateOf(false) }
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            profileImageUrl = it.toString()
            showDialog.value = false
        }


    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "com.dailycloud.dailycloud" + ".provider", file
    )


    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            profileImageUrl = uri.toString()
            showDialog.value = false
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        IconButton(onClick = backToProfile) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.Black,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                ,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .wrapContentSize(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Box(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(220.dp)
                                .border(
                                    BorderStroke(5.dp, SolidColor(Primary)),
                                    shape = CircleShape
                                )
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                        data  = Uri.parse(profileImageUrl)
                                    ),
                                contentDescription = "Profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.Center).align(Alignment.Center)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(color = Primary, shape = CircleShape)
                                .align(Alignment.BottomEnd).clickable { showDialog.value = true }
                        ) {

                            if (showDialog.value) {
                                AlertDialog(
                                    onDismissRequest = { showDialog.value = false },
                                    title = { Text("Choose method") },
                                    confirmButton = {
                                        Column(Modifier.padding(8.dp).fillMaxWidth()){
                                            Button(onClick = {galleryLauncher.launch("image/*")},
                                                Modifier.fillMaxWidth(),
                                                colors = buttonColors(Primary)
                                            ) {
                                                Text("Galery")
                                            }
                                            Button(onClick = {
                                                    val permissionCheckResult =
                                                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                                                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                                        cameraLauncher.launch(uri)
                                                    } else {
                                                        // Request a permission
                                                        permissionLauncher.launch(Manifest.permission.CAMERA)
                                                    }
                                            },
                                                Modifier.fillMaxWidth(),
                                                colors = buttonColors(Primary)) {
                                                Text("Camera")
                                            }
                                        }
                                    },
                                    dismissButton = null,
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .align(Alignment.Center),
                                tint = Color.White,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = nameEdit.value!!,
                            onValueChange = { nameEdit.value = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            label = { Text("Name", color = Primary) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.AccountCircle,
                                    contentDescription = "Name",
                                    tint = Primary
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text
                            ),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Primary,
                                unfocusedBorderColor = Primary
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            CustomOutlinedButton(
                onClick = { viewModel.updateUser(backToProfile, nameEdit.value!!) },
                text = stringResource(R.string.update).uppercase(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}