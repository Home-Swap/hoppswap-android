package com.hoppswap.feature.property.details

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
object PropertyDetailsScreen

@Composable
fun PropertyDetailsScreen(controller: NavHostController, viewModel: PropertyDetailsViewModel = hiltViewModel()) {

}