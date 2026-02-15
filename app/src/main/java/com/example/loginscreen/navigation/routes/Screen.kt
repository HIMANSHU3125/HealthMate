package com.example.loginscreen.navigation.routes

sealed class Screen(val route: String){
    data object Intro: Screen(route = "intro")
    data object Home: Screen(route = "home")
    data object TopDoctors: Screen(route = "topDoctor")
    data object Detail: Screen(route = "detail")
}