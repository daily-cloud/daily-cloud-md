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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
<<<<<<< HEAD
=======
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
>>>>>>> 4ecbc408fae4575d17481a68396411775a8ee978
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.common.UiState
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme
import com.dailycloud.dailycloud.ui.theme.Primary
import com.dailycloud.dailycloud.util.Util.dateFormat
import com.google.firebase.Timestamp

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
                    backToContents = navigateBack,
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
    backToContents: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
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
=======
        Box() {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            )
            IconButton(onClick = backToContents) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
>>>>>>> 4ecbc408fae4575d17481a68396411775a8ee978
                )
            }
        }
        Text(
            text = title,
            style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {

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
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        )
    }
}

@Preview
@Composable
fun ContentPreview() {
    DailyCloudTheme() {
        Surface() {
            DetailContent(
                photoUrl = "",
                title = "Kamu sering dengar “aduh kena mental”, lantas apa itu kesehatan mental?",
                "Sebagian dari kita mungkin sering mendegar kata-kata “aduh mental gue kena” dan istilah sejenis. Tetapi, kamu tahu **ga** apa itu kesehatan mental? Jangan-jangan kamu sering dengar atau bahkan mengakatan istilah tersebut tanpa tahu makna dari istilah tersebut. Kalau belum, tenang saja, yuk sama-sama belajar mengenai apa itu kesehatan mental.\n" +
                        "\n" +
                        "Menurut organisasi kesehatan dunia, WHO, Kesehatan mental adalah keadaan kesejahteraan mental yang memungkinkan orang mengatasi tekanan hidup, menyadari kemampuan mereka, belajar dengan baik dan bekerja dengan baik, dan berkontribusi pada komunitas mereka.\n" +
                        "\n" +
                        "Kesehatan mental lebih dari tidak adanya gangguan mental. Itu ada dalam rangkaian kesatuan yang kompleks, yang dialami secara berbeda dari satu orang ke orang lain, dengan tingkat kesulitan dan kesusahan yang berbeda-beda dan hasil sosial dan klinis yang berpotensi sangat berbeda\n" +
                        "\n" +
                        "Nah, jadi itu ya mengenai apa itu kesehatan mental. Jadi, setelah membaca ini, teman-teman bisa memahami apa itu kesehatan mental",
                "Daily Cloud",
                "31 May 2021",
                {}
            )
        }
    }
}