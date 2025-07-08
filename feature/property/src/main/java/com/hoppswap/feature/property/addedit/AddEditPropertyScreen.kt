package com.hoppswap.feature.property.addedit

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
object AddEditPropertyScreen

@Composable
fun AddEditPropertyScreen(controller: NavHostController, viewModel: AddEditPropertyViewModel = hiltViewModel()) {
}