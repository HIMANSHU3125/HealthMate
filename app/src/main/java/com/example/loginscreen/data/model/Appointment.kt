package com.example.loginscreen.data.model

data class Appointment(
    val id: String = "",
    val doctorId: String = "",
    val doctorName: String = "",
    val userId: String = "",
    val patientName: String = "",
    val patientPhone: String = "",
    val date: String = "",
    val time: String = "",
    val status: String = "Upcoming" // Upcoming, Completed, Cancelled
)
