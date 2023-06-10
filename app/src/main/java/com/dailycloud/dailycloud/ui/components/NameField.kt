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
fun NameField(
    modifier: Modifier = Modifier,
    text:String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = LocalTextStyle.current,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        decorationBox = { innerTextField ->
            Box() {
                if (value.isEmpty()) {
                    Text(
                        text = text,
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
fun NameFieldPreview() {
    DailyCloudTheme() {
        NameField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth(), text = "First Name")
    }
}