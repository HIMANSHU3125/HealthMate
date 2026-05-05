package com.example.loginscreen.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
object IntroRoute

@Serializable
object LoginRoute

@Serializable
object SignUpRoute

@Serializable
object HomeRoute

@Serializable
object TopDoctorsRoute

@Serializable
object DetailRoute

@Serializable
data class BookingRoute(val doctorId: String, val doctorName: String)
