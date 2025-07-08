package com.hoppswap.feature.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Composable
fun HomeScreen(controller: NavHostController, viewModel: HomeScreenViewModel = hiltViewModel()) {
}