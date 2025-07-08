package com.hoppswap.feature.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.hoppswap.core.designsystem.component.FormError
import com.hoppswap.core.designsystem.component.Heading
import com.hoppswap.core.designsystem.component.PrimaryButton
import com.hoppswap.core.designsystem.theme.Large
import com.hoppswap.core.designsystem.theme.Medium
import com.hoppswap.core.designsystem.theme.Small
import com.hoppswap.core.designsystem.theme.XLarge
import com.hoppswap.feature.auth.R
import kotlinx.serialization.Serializable

@Serializable
object LoginScreen

@Composable
fun LoginScreen(userLoggedIn: () -> Unit, viewModel: LoginViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.action.collect {
            when (it) {
                LoginAction.NavigateToHomeScreen -> userLoggedIn()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Large),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(com.hoppswap.core.designsystem.R.drawable.logo),
                contentDescription = stringResource(R.string.cont_desc_logo),
                modifier = Modifier.padding(bottom = XLarge)
            )

            Heading(
                text = R.string.label_sign_in,
                modifier = Modifier.padding(bottom = XLarge)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.label_email)) },
                leadingIcon = { Icon(painterResource(R.drawable.ic_mail), contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(Medium))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.label_password)) },
                leadingIcon = { Icon(painterResource(R.drawable.ic_lock), contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                            contentDescription = stringResource(
                                if (passwordVisible) R.string.cont_desc_hide_password else
                                    R.string.cont_desc_show_password
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(Large))

            uiState.error?.let {
                FormError(
                    text = if (it == LoginError.InvalidEmail) R.string.login_error_email else R.string.login_error_auth,
                    modifier = Modifier.padding(bottom = Small)
                )
            }

            PrimaryButton(
                R.string.button_login,
                !uiState.loading && email.isNotBlank() && password.isNotBlank(),
                uiState.loading
            ) { viewModel.onIntent(LoginIntent.OnLoginSubmitted(email, password)) }
        }
    }
}