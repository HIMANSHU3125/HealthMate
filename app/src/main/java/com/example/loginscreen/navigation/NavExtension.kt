package com.example.loginscreen.navigation

import androidx.navigation.NavController
import androidx.navigation.internal.NavContext
import com.example.loginscreen.core.model.DoctorModel
import com.example.loginscreen.navigation.routes.Screen

fun NavController.navigateToDetail(doctor: DoctorModel){
    currentBackStackEntry?.savedStateHandle?.set("doctor",doctor)
    navigate(route = Screen.Detail.route)
}