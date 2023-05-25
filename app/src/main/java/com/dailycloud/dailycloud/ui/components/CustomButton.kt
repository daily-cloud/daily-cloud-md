package com.dailycloud.dailycloud.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme
import com.dailycloud.dailycloud.ui.theme.Primary

@Composable
fun CustomFilledButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Primary),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Text(text, color = Color.White, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun CustomOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
) {
    OutlinedButton(
        modifier = modifier,
        border = BorderStroke(1.dp, Primary),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Text(text, color = Primary, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SocialButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    Icon: ImageVector,
) {
    OutlinedButton(
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = Color.Black),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Icon(imageVector = Icons.Default.Email, contentDescription = text)
        Text(text, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
    }
}