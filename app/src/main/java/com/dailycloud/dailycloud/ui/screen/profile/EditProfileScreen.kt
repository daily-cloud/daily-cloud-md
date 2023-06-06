package com.dailycloud.dailycloud.ui.screen.profile


import android.net.Uri
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.components.CustomOutlinedButton
import com.dailycloud.dailycloud.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    updateProfile: () -> Unit,
    backToProfile: () -> Unit,
    toCamera: () -> Unit,
) {

    var profileImageUrl = "https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
    val showDialog = remember { mutableStateOf(false) }
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            profileImageUrl = it.toString()
            showDialog.value = false
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
                .padding(16.dp),
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
                                            Button(onClick = { toCamera() },
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

                    val nameEdit = remember { mutableStateOf(viewModel.name.value) }
                    val emailState = remember { mutableStateOf(viewModel.email.value) }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = nameEdit.value,
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

                        OutlinedTextField(
                            value = emailState.value,
                            onValueChange = { emailState.value = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            label = { Text("Email", color = Primary) },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Email,
                                    contentDescription = "Email",
                                    tint = Primary
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Email
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
                onClick = { updateProfile() },
                text = stringResource(R.string.update).uppercase(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}