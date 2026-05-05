package com.example.loginscreen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.loginscreen.core.ViewModel.MainViewModel
import com.example.loginscreen.navigation.routes.IntroRoute
import com.example.loginscreen.navigation.routes.HomeRoute
import com.example.loginscreen.navigation.routes.TopDoctorsRoute
import com.example.loginscreen.navigation.routes.DetailRoute
import com.example.loginscreen.navigation.routes.LoginRoute
import com.example.loginscreen.navigation.routes.BookingRoute
import com.example.loginscreen.navigation.routes.introRoute
import com.example.loginscreen.navigation.routes.homeRoute
import com.example.loginscreen.navigation.routes.topDoctorsRoute
import com.example.loginscreen.navigation.routes.detailRoute
import androidx.navigation.toRoute

@Composable
fun AppNavGraph(
    nav: NavHostController,
    vm: MainViewModel,

){
    NavHost(navController = nav, startDestination = IntroRoute){
        introRoute (
            onStart = {
                nav.navigate(LoginRoute){
                    popUpTo(IntroRoute){inclusive=true}
                }
            },
            onAutoLogin = { role ->
                if (role == "doctor") {
                    nav.navigate(com.example.loginscreen.navigation.routes.DoctorDashboardRoute) {
                        popUpTo(IntroRoute) { inclusive = true }
                    }
                } else {
                    nav.navigate(HomeRoute) {
                        popUpTo(IntroRoute) { inclusive = true }
                    }
                }
            }
        )
        composable<LoginRoute> {
            com.example.loginscreen.feature.LoginPAge.loginScreen(
                onLoginSuccess = { role ->
                    if (role == "doctor") {
                        nav.navigate(com.example.loginscreen.navigation.routes.DoctorDashboardRoute) {
                            popUpTo(LoginRoute) { inclusive = true }
                        }
                    } else {
                        nav.navigate(HomeRoute) {
                            popUpTo(LoginRoute) { inclusive = true }
                        }
                    }
                }
            )
        }
        
        composable<com.example.loginscreen.navigation.routes.DoctorDashboardRoute> {
            com.example.loginscreen.feature.DoctorDashboard.DoctorDashboardScreen(
                onLogout = {
                    com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                    nav.navigate(LoginRoute) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        homeRoute(vm=vm,
            onOpenDoctorDetail = {doctorModel -> nav.navigateToDetail(doctorModel)},
            onOpenTopDoctors={nav.navigate(TopDoctorsRoute)},
            onLogout = {
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                nav.navigate(LoginRoute) {
                    popUpTo(0) { inclusive = true }
                }
            }
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

        composable<BookingRoute> { backStackEntry ->
            val bookingRoute = backStackEntry.toRoute<BookingRoute>()
            com.example.loginscreen.feature.booking.BookingScreen(
                doctorId = bookingRoute.doctorId,
                doctorName = bookingRoute.doctorName,
                onBack = { nav.popBackStack() },
                onBookingSuccess = {
                    nav.popBackStack(HomeRoute, inclusive = false)
                }
            )
        }
    }

}