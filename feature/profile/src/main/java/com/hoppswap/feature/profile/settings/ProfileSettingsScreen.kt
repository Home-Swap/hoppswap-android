package com.hoppswap.feature.profile.settings

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
object ProfileSettingsScreen

@Composable
fun ProfileSettingsScreen(controller: NavHostController, viewModel: ProfileSettingsViewModel = hiltViewModel()) {
}