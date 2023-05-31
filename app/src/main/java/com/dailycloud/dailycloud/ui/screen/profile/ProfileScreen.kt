package com.dailycloud.dailycloud.ui.screen.profile

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.components.CustomFilledButton
import com.dailycloud.dailycloud.ui.components.CustomOutlinedButton
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    toGetStarted: () -> Unit,
) {
    val profileImageUrl = "https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
    val name by viewModel.name
    val email by viewModel.email

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
                AsyncImage(
                    model = profileImageUrl,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Name",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )

                Text(
                    text = name,
                    style = TextStyle(fontSize = 24.sp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Email",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )

                Text(
                    text = email,
                    style = TextStyle(fontSize = 24.sp)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        CustomOutlinedButton(onClick = { viewModel.signOut(toGetStarted) }, text = stringResource(R.string.log_out).uppercase(), modifier = Modifier.fillMaxWidth())
    }
}

