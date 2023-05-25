package com.dailycloud.dailycloud.ui.screen.getstarted

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.components.CustomFilledButton
import com.dailycloud.dailycloud.ui.components.CustomOutlinedButton
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme
import com.dailycloud.dailycloud.ui.theme.Primary

@Composable
fun GetStartedScreen(
    modifier: Modifier = Modifier,
    toLogin: () -> Unit,
    toSignUp: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.smiling),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier.size(160.dp)
            )
            Text(
                stringResource(R.string.welcome_to),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.displaySmall,
                color = Primary
            )
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                stringResource(R.string.we_re_so_glad_to_see_you_here),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                stringResource(R.string.we_help_you_get_stronger_emotionally),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column() {
            CustomFilledButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.sign_up).uppercase(),
                onClick = { toSignUp() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.i_have_an_account).uppercase(),
                onClick = { toLogin() }
            )
        }
    }
}