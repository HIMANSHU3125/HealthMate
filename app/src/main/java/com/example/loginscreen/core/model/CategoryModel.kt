package com.example.loginscreen.core.model

import com.google.firebase.firestore.PropertyName

data class CategoryModel(
    @get:PropertyName("Id") @set:PropertyName("Id") var Id: Int = 0,
    @get:PropertyName("Name") @set:PropertyName("Name") var Name: String = "",
    @get:PropertyName("Picture") @set:PropertyName("Picture") var Picture: String = ""
)