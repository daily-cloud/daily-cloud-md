package com.dailycloud.dailycloud.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dailycloud.dailycloud.ui.theme.Primary

@Composable
fun CustomFilledButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Primary),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(16.dp),
        enabled = enabled,
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
    enabled: Boolean = true,
) {
    OutlinedButton(
        modifier = modifier,
        border = BorderStroke(1.dp, Primary),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(16.dp),
        enabled = enabled,
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
    icon: Painter,
) {
    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Icon(painter = icon, contentDescription = text, modifier = Modifier.size(24.dp))
        Text(text, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, color = Color.Black, modifier = Modifier.weight(1f))
    }
}