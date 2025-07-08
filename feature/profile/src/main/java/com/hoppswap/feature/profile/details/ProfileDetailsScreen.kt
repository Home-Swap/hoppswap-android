package com.hoppswap.feature.profile.details

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
object ProfileDetailsScreen

@Composable
fun ProfileDetailsScreen(controller: NavHostController, viewModel: ProfileDetailsViewModel = hiltViewModel()) {
}