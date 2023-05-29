package com.dailycloud.dailycloud.ui.screen.signup

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.google.firebase.auth.GoogleAuthProvider.getCredential

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    toLogin: () -> Unit,
    toHome: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val email by viewModel.email
    val password by viewModel.password
    val confirmPassword by viewModel.confirmPassword
    val isAgree by viewModel.isAgree

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            try {
                val credentials = viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credentials.googleIdToken
                val googleCredentials = getCredential(googleIdToken, null)
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

    viewModel.isSignUpWithEmail.collectAsState().value.let { isSignUpWithEmail ->
        if (isSignUpWithEmail) {
            SignUpEmailContent(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                isAgree = isAgree,
                onEmailChange = viewModel::onEmailChanged,
                onPasswordChange = viewModel::onPasswordChanged,
                onConfirmPasswordChange = viewModel::onConfirmPasswordChanged,
                onAgreeChange = viewModel::onAgreeChanged,
                onSignUp = { viewModel.signUp(toHome)  },
                toLogin = toLogin,
            )
        } else {
            MultiSignUpContent(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                toSignUpWithEmail = { viewModel.toSignUpWithEmail() },
                oneTapSignIn = { viewModel.oneTapSignIn { launch(it) } },
            )
        }
    }
}

@Composable
fun MultiSignUpContent(
    modifier: Modifier = Modifier,
    toSignUpWithEmail: () -> Unit,
    oneTapSignIn: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            stringResource(R.string.app_name),
            style = MaterialTheme.typography.displaySmall,
            color = Primary,
            fontWeight = FontWeight.SemiBold,
        )
        Image(
            painter = painterResource(R.drawable.smiling),
            contentDescription = stringResource(R.string.logo),
            modifier = Modifier.size(160.dp)
        )
        Text(
            stringResource(R.string.a_step_closer_to_self_care),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Image(painter = painterResource(R.drawable.line), contentDescription = "line")
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.create_an_account_take_a_step_toward_self_care),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        SocialButton(
            onClick = oneTapSignIn,
            text = stringResource(R.string.sign_up_with_google),
            Icon = Icons.Default.AlternateEmail
        )
        SocialButton(
            onClick = { toSignUpWithEmail() },
            text = stringResource(R.string.sign_up_with_email),
            Icon = Icons.Default.Email
        )
    }
}

@Composable
fun SignUpEmailContent(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    confirmPassword: String,
    isAgree: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onAgreeChange: (Boolean) -> Unit,
    onSignUp: () -> Unit,
    toLogin: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Text(stringResource(R.string.sign_up), style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(24.dp))
        Row() {
            Text(stringResource(R.string.already_have_an_account))
            Spacer(modifier = Modifier.width(4.dp))
            Text(stringResource(R.string.login), color = Primary, modifier = Modifier.clickable { toLogin() })
        }
        Spacer(modifier = Modifier.height(48.dp))
        EmailField(
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(
            value = confirmPassword,
            hint = stringResource(R.string.confirm_password),
            onValueChange = onConfirmPasswordChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(checked = isAgree, onCheckedChange = onAgreeChange, colors = CheckboxDefaults.colors(Primary))
            Text(stringResource(R.string.i_agree_to_terms_conditions))
        }
        Text(
            stringResource(R.string.agreement),
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomFilledButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSignUp,
            text = stringResource(R.string.continues),
        )
    }
}