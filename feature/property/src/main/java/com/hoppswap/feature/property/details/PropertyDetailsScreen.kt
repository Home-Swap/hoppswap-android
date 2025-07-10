package com.hoppswap.feature.property.details

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.hoppswap.data.auth.model.Property
import kotlinx.serialization.Serializable

@Serializable
object PropertyDetailsScreen

@Composable
fun PropertyDetailsScreen(property: Property, viewModel: PropertyDetailsViewModel = hiltViewModel()) {

}