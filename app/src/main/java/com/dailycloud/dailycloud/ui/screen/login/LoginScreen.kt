package com.dailycloud.dailycloud.ui.screen.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.components.CustomFilledButton
import com.dailycloud.dailycloud.ui.components.EmailField
import com.dailycloud.dailycloud.ui.components.PasswordField
import com.dailycloud.dailycloud.ui.components.SocialButton
import com.dailycloud.dailycloud.ui.theme.Primary
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    toHome: () -> Unit,
    toSignUp: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val credentials = viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credentials.googleIdToken
                val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
                viewModel.signInWithGoogle(googleCredentials, toHome)
            } catch (it: ApiException) {
                print(it)
            }
        }
    }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

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
            Text(stringResource(R.string.sign_up), color = Primary, modifier = Modifier.clickable { toSignUp() })
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("OR", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))
        SocialButton(onClick = { viewModel.oneTapSignIn { launch(it) } }, text = stringResource(R.string.log_in_with_google), icon = painterResource(R.drawable.google))
    }
}