package com.example.loginscreen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.loginscreen.core.ViewModel.MainViewModel
import com.example.loginscreen.navigation.routes.Screen
import com.example.loginscreen.navigation.routes.detailRoute
import com.example.loginscreen.navigation.routes.homeRoute
import com.example.loginscreen.navigation.routes.introRoute
import com.example.loginscreen.navigation.routes.topDoctorsRoute

@Composable
fun AppNavGraph(
    nav: NavHostController,
    vm: MainViewModel,

){
    NavHost(navController = nav, startDestination = Screen.Intro.route){
        introRoute (
            onStart = {
                nav.navigate(Screen.Home.route){
                    popUpTo(Screen.Intro.route){inclusive=true}

                }
            }
        )
        homeRoute(vm=vm,
            onOpenDoctorDetail = {doctorModel -> nav.navigateToDetail(doctorModel)},
            onOpenTopDoctors={nav.navigate(Screen.TopDoctors.route)}

        )
        topDoctorsRoute(
            vm=vm,
            onback = {nav.popBackStack()},
            onopenDetail = {doctorModel -> nav.navigateToDetail(doctor=doctorModel)}
        )

        detailRoute(
            nav=nav,
            onBack = {nav.popBackStack()}
        )
    }

}