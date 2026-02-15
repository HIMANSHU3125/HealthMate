package com.example.loginscreen.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoctorModel(
    val Id: Int = 0,
    val Name: String = "",
    val Address: String = "",
    val Location: String = "",
    val Mobile: String = "",
    val Patiens: String = "",
    val Picture: String = "",
    val Rating: Float = 0f,
    val Site: String = "",
    val Special: String = "",
    val Biography: String = "",
    val Expriense: Int = 0
) : Parcelable  //