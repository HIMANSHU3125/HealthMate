package com.example.loginscreen.navigation

import androidx.navigation.NavController
import com.example.loginscreen.core.model.DoctorModel
import com.example.loginscreen.navigation.routes.DetailRoute

fun NavController.navigateToDetail(doctor: DoctorModel){
    currentBackStackEntry?.savedStateHandle?.set("doctor",doctor)
    navigate(DetailRoute)
}