package com.dailycloud.dailycloud.ui.screen.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.components.CustomFilledButton
import com.dailycloud.dailycloud.ui.components.EmailField
import com.dailycloud.dailycloud.ui.components.PasswordField
import com.dailycloud.dailycloud.ui.components.SocialButton
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme
import com.dailycloud.dailycloud.ui.theme.Primary

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    toHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    Column(modifier = modifier
        .verticalScroll(rememberScrollState())
        .padding(16.dp)) {

        val email by viewModel.email
        val password by viewModel.password

        Text(stringResource(R.string.login) , style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(24.dp))
        EmailField(
            value = email,
            onValueChange = viewModel::onEmailChanged,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(
            value = password,
            onValueChange = viewModel::onPasswordChanged,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomFilledButton(
            text = "Login".uppercase(),
            onClick = {
                viewModel.login(toHome)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            Text(stringResource(R.string.don_t_have_an_account))
            Text(stringResource(R.string.sign_up), color = Primary, modifier = Modifier.clickable {  })
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("OR", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))
        SocialButton(onClick = {}, text = stringResource(R.string.log_in_with_google), Icon = Icons.Default.Email)
    }
}