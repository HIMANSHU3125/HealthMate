package com.example.loginscreen.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.loginscreen.feature.intro.IntroScreen

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loginscreen.ui.viewmodel.AuthStatus
import com.example.loginscreen.ui.viewmodel.AuthViewModel

fun NavGraphBuilder.introRoute(onStart: () -> Unit, onAutoLogin: (String) -> Unit) {
    composable<IntroRoute> {
        val authViewModel: AuthViewModel = viewModel()
        val authStatus by authViewModel.authStatus.collectAsState()

        LaunchedEffect(Unit) {
            authViewModel.checkUserStatus()
        }

        LaunchedEffect(authStatus) {
            if (authStatus is AuthStatus.Success) {
                onAutoLogin((authStatus as AuthStatus.Success).role)
            }
        }

        IntroScreen(onStartClick = onStart)
    }
}