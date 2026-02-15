package com.example.loginscreen.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.loginscreen.feature.intro.IntroScreen

fun NavGraphBuilder.introRoute(onStart:()-> Unit){
    composable (route = Screen.Intro.route){
        IntroScreen(onStartClick = onStart )
    }
}