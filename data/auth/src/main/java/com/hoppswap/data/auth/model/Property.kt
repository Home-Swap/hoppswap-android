package com.hoppswap.data.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Property(
    @SerialName("_id") val id: String,
    val owner: User,
    val title: String,
    val address: String,
    val city: String,
    val country: String,
    val zipcode: String? = null,
    val lat: Double,
    val lng: Double,
    val description: String,
    val amenities: List<Amenity>,
    val people: Int,
    val photos: List<String>,
)

@Serializable
data class Amenity(
    val category: String,
    val name: String
)
