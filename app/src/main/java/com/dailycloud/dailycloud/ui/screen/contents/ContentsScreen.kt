package com.dailycloud.dailycloud.ui.screen.contents

import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.data.local.model.Content
import com.dailycloud.dailycloud.ui.common.UiState
import com.dailycloud.dailycloud.ui.components.ContentListItem
import com.dailycloud.dailycloud.util.Util
import com.dailycloud.dailycloud.util.Util.dpToPx

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
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.content),
            style = MaterialTheme.typography.displaySmall,
        )
        Text(
            text = stringResource(R.string.content_quote),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = rememberLazyListState(),
        ) {
            item {
                VideoPlayer("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(contentList) { data ->
                ContentListItem(
                    title = data.title,
                    articlePreview = Util.reduceText(data.article, 100),
                    photoUrl = data.photoPath,
                    onClick = { navigateToContentDetail(data.contentId) },
                )
            }
        }
    }
}

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun VideoPlayer(url: String) {
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context).build()
    val mediaItem = MediaItem.fromUri(Uri.parse(url))
    exoPlayer.setMediaItem(mediaItem)

    val playerView = PlayerView(context)
    playerView.player = exoPlayer
    playerView.setShowShuffleButton(false)
    playerView.setShowNextButton(false)
    playerView.setShowPreviousButton(false)
    playerView.setShowFastForwardButton(false)
    playerView.setShowRewindButton(false)
    playerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300.dpToPx())

    DisposableEffect(
        AndroidView(
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            factory = { playerView }
        )
    ) {

        exoPlayer.prepare()
        exoPlayer.playWhenReady = false

        onDispose {
            exoPlayer.release()
        }
    }
}

