package com.example.loginscreen.navigation.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.loginscreen.core.ViewModel.MainViewModel
import com.example.loginscreen.core.model.DoctorModel
import com.example.loginscreen.feature.Home.MainScreen

fun NavGraphBuilder.homeRoute(
    vm: MainViewModel,
    onOpenTopDoctors: () -> Unit,
    onOpenDoctorDetail:(DoctorModel)-> Unit


){
    composable(Screen.Home.route){
        val categorie by vm.category.observeAsState(emptyList())
        val doctor by vm.doctors.observeAsState(emptyList())

        LaunchedEffect(Unit) {
            if(categorie.isEmpty()) vm.loadCategory()
            if(doctor.isEmpty()) vm.loadDoctors()
        }
        MainScreen(viewModel=vm,
            onOpenTopDoctors=onOpenTopDoctors,
        onOpenDoctorDetail=onOpenDoctorDetail
        )


    }

}