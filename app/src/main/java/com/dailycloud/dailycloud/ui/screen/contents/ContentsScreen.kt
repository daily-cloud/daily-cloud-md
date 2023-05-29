package com.dailycloud.dailycloud.ui.screen.contents


import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.data.local.model.Content
import com.dailycloud.dailycloud.ui.common.UiState
import com.dailycloud.dailycloud.ui.components.ContentListItem
import com.dailycloud.dailycloud.util.Util
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun ContentsScreen(
    modifier: Modifier = Modifier,
    viewModel: ContentsViewModel = hiltViewModel(),
    navigateToContentDetail: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getContents()
            }
            is UiState.Success -> {
                Content(
                    contentList = uiState.data,
                    modifier = modifier,
                    navigateToContentDetail = navigateToContentDetail,
                )
            }
            is UiState.Error -> {}
        }
    }

}
@Composable
fun Content(
    contentList: List<Content>,
    modifier: Modifier = Modifier,
    navigateToContentDetail: (String) -> Unit,
) {

    Box(modifier = modifier) {
        Column(
            Modifier.padding(top = 40.dp, start = 40.dp, end = 40.dp)
        ) {
            Text(
                text = stringResource(R.string.content),
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
            )
            Text(
                text = stringResource(R.string.content_quote),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            AndroidView(factory = {
                val view = YouTubePlayerView(it)
                view.addYouTubePlayerListener(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            youTubePlayer.loadVideo("hsfScVJgGOw", 0f)
                        }
                    }
                )
                view
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(bottom = 20.dp))

            LazyColumn{
                items(contentList) { data ->
                    ContentListItem(
                        title = data.title,
                        articlePreview = Util.reduceText(data.article, 100),
                        photoUrl = data.photoPath,
                        modifier = Modifier.clickable{ navigateToContentDetail(data.contentId)}
                            .fillMaxWidth().padding(top=35.dp))
                }
            }
        }
    }
}

