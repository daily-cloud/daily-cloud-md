package com.dailycloud.dailycloud.ui.screen.profile

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProfileScreen() {
    val profileImageUrl = "https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
    val name = "John Doe"
    val email = "johndoe@example.com"
    val bio = "Lorem ipsum dolor sit amet, consectetur adipiscing elit Lorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elit..."

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentSize(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
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

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Bio",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )

                Text(
                    text = bio,
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}

