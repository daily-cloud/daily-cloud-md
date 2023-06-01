package com.dailycloud.dailycloud.ui.screen.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.common.UiState
import com.dailycloud.dailycloud.util.Util.dateFormat

@Composable
fun ContentScreen(
    contentId: String,
    viewModel: ContentViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getContentById(contentId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.photoPath,
                    data.title,
                    data.article,
                    data.author,
                    dateFormat(data.createdAt),
                    navigateBack
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    photoUrl: String,
    title: String,
    article: String,
    author: String,
    releaseDate: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Box {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier.padding(16.dp).clickable { onBackClick() }
                )

                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                )
            }


            Text(
                text = title,
                style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 16.dp)
            )
            Row{

                Text(
                    text = author,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = releaseDate,
                    style = TextStyle(fontSize = 16.sp, fontStyle = FontStyle.Italic),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }


            Text(
                text = article,
                style = TextStyle(fontSize = 16.sp, lineHeight = 20.sp),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}