package com.dailycloud.dailycloud.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme

@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = LocalTextStyle.current,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        decorationBox = { innerTextField ->
            Box() {
                if (value.isEmpty()) {
                    Text(
                        text = "Email Address",
                        style = TextStyle(color = Color.Gray),
                    )
                }
            }
            innerTextField()
        },
        modifier = modifier.clip(RoundedCornerShape(8.dp)).background(Color(0xFFF4F7FC)).padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun EmailFieldPreview() {
    DailyCloudTheme() {
        EmailField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
    }
}