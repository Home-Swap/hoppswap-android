package com.hoppswap.feature.auth.forgotpassword

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
object ForgotPasswordScreen

@Composable
fun ForgotPasswordScreen(controller: NavHostController, viewModel: ForgotPasswordViewModel = hiltViewModel()) {
}