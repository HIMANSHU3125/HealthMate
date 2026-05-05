package com.example.loginscreen.data.model

data class UserModel(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val role: String = "patient", // "doctor" or "patient"
    val profileImage: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
