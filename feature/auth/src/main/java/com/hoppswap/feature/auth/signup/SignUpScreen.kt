package com.hoppswap.feature.auth.signup

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
object SignUpScreen

@Composable
fun SignUpScreen(controller: NavHostController, viewModel: SignUpViewModel = hiltViewModel()) {
}