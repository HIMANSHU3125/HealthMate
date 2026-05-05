package com.example.loginscreen.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import com.google.firebase.firestore.PropertyName

@Parcelize
data class DoctorModel(
    @get:PropertyName("Id") @set:PropertyName("Id") var Id: Int = 0,
    @get:PropertyName("Name") @set:PropertyName("Name") var Name: String = "",
    @get:PropertyName("Address") @set:PropertyName("Address") var Address: String = "",
    @get:PropertyName("Location") @set:PropertyName("Location") var Location: String = "",
    @get:PropertyName("Mobile") @set:PropertyName("Mobile") var Mobile: String = "",
    @get:PropertyName("Patiens") @set:PropertyName("Patiens") var Patiens: String = "",
    @get:PropertyName("Picture") @set:PropertyName("Picture") var Picture: String = "",
    @get:PropertyName("Rating") @set:PropertyName("Rating") var Rating: Double = 0.0,
    @get:PropertyName("Site") @set:PropertyName("Site") var Site: String = "",
    @get:PropertyName("Special") @set:PropertyName("Special") var Special: String = "",
    @get:PropertyName("Biography") @set:PropertyName("Biography") var Biography: String = "",
    @get:PropertyName("Expriense") @set:PropertyName("Expriense") var Expriense: Int = 0
) : Parcelable