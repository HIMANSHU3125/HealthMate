package com.example.loginscreen.feature.Home

import android.telephony.ims.SipDetails
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.loginscreen.core.ViewModel.MainViewModel
import com.example.loginscreen.core.model.CategoryModel
import com.example.loginscreen.core.model.DoctorModel

@Composable
fun MainScreen(viewModel: MainViewModel,
               onOpenTopDoctors:()-> Unit,
               onOpenDoctorDetail:(DoctorModel)-> Unit) {

    val categories by viewModel.category.observeAsState(initial=emptyList())
    val doctors by viewModel.doctors.observeAsState(initial = emptyList())
    LaunchedEffect(Unit) {
        if(categories.isEmpty()) viewModel.loadCategory()
        if(doctors.isEmpty()) viewModel.loadDoctors()
    }

    var selectedBottom by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            HomeBottomBar(
                selected = selectedBottom,
                onselected = { selectedBottom = it }
            )
        }
    ) { inner->
        LazyColumn(contentPadding = inner) {
            item { HomeHeader() }
            item { Banner() }
            item {SectionHeader(title = "Doctor Speciality", onSeeAll = {  }) }
            item { CategoryRow(item=categories,onClick={}) }
            item {SectionHeader(title = "Top Doctors", onSeeAll = onOpenTopDoctors) }
            item {
                DoctorRow(
                    items=doctors,
                    onClick = onOpenDoctorDetail)}

        }



    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    // Mock Data for the preview
    val mockCategories = listOf(
        CategoryModel(1, "Cardiology", "ic_cardio"),
        CategoryModel(2, "Dental", "ic_dental"),
        CategoryModel(3, "Neurology", "ic_neuro")
    )

    val mockDoctors = listOf(
        DoctorModel(1, "Dr. Sarah Smith", "Cardiologist", "4.8", "exp_1"),
        DoctorModel(2, "Dr. John Doe", "Dermatologist", "4.9", "exp_2")
    )

    // Using Scaffold directly to visualize the layout without the ViewModel
    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            HomeBottomBar(selected = 0, onselected = {})
        }
    ) { inner ->
        LazyColumn(contentPadding = inner) {
            item { HomeHeader() }
            item { Banner() }
            item { SectionHeader(title = "Doctor Speciality", onSeeAll = {}) }
            item { CategoryRow(item = mockCategories, onClick = {}) }
            item { SectionHeader(title = "Top Doctors", onSeeAll = {}) }
            item { DoctorRow(items = mockDoctors, onClick = {}) }
        }
    }
}



