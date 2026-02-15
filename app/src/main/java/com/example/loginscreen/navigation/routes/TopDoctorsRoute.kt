package com.example.loginscreen.navigation.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.loginscreen.core.ViewModel.MainViewModel
import com.example.loginscreen.core.model.DoctorModel
import com.example.loginscreen.feature.TopDoctors.TopDoctorScreen

fun NavGraphBuilder.topDoctorsRoute(
    vm: MainViewModel,
    onback:()-> Unit,
    onopenDetail:(DoctorModel)-> Unit
){
    composable (Screen.TopDoctors.route){
        val doctors by vm.doctors.observeAsState(initial=emptyList())
        LaunchedEffect(Unit) {if (doctors.isEmpty()) vm.loadDoctors() }

        TopDoctorScreen(
            doctors = doctors,
            onBack = onback,
            onOpenDetail = onopenDetail
        )



    }

}