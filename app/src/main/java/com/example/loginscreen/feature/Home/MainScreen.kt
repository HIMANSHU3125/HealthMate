package com.example.loginscreen.feature.Home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loginscreen.core.ViewModel.MainViewModel
import com.example.loginscreen.core.model.CategoryModel
import com.example.loginscreen.core.model.DoctorModel

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    userViewModel: com.example.loginscreen.ui.viewmodel.UserViewModel = viewModel(),
    onOpenTopDoctors: () -> Unit,
    onOpenDoctorDetail: (DoctorModel) -> Unit,
    onLogout: () -> Unit
) {

    val categories by viewModel.category.observeAsState(initial=emptyList())
    val doctors by viewModel.doctors.observeAsState(initial = emptyList())
    val userProfile by userViewModel.userProfile.collectAsState()

    LaunchedEffect(Unit) {
        if(categories.isEmpty()) viewModel.loadCategory()
        if(doctors.isEmpty()) viewModel.loadDoctors()
    }

    var selectedBottom by remember { mutableStateOf(0) }

    val error by viewModel.error.observeAsState()

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            HomeBottomBar(
                selected = selectedBottom,
                onselected = { selectedBottom = it }
            )
        }
    ) { inner->
        Box(modifier = Modifier.padding(inner)) {
            if (error != null) {
                androidx.compose.material3.Text(
                    text = "Error: $error",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                when (selectedBottom) {
                    0 -> HomeContent(categories, doctors, userProfile?.name ?: "User", onOpenTopDoctors, onOpenDoctorDetail)
                    1 -> WishlistScreen()
                    2 -> SettingScreen()
                    3 -> AccountScreen(userProfile = userProfile, onLogout = onLogout)
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    categories: List<CategoryModel>,
    doctors: List<DoctorModel>,
    userName: String,
    onOpenTopDoctors: () -> Unit,
    onOpenDoctorDetail: (DoctorModel) -> Unit
) {
    LazyColumn {
        item { HomeHeader(userName) }
        item { Banner() }
        item { SectionHeader(title = "Doctor Speciality", onSeeAll = {  }) }
        item { CategoryRow(item=categories,onClick={}) }
        item { SectionHeader(title = "Top Doctors", onSeeAll = onOpenTopDoctors) }
        item {
            DoctorRow(
                items=doctors,
                onClick = onOpenDoctorDetail
            )
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
            item { HomeHeader("User") }
            item { Banner() }
            item { SectionHeader(title = "Doctor Speciality", onSeeAll = {}) }
            item { CategoryRow(item = mockCategories, onClick = {}) }
            item { SectionHeader(title = "Top Doctors", onSeeAll = {}) }
            item { DoctorRow(items = mockDoctors, onClick = {}) }
        }
    }
}



