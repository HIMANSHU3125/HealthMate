package com.example.loginscreen.feature.DoctorDashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loginscreen.ui.viewmodel.DoctorDashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDashboardScreen(
    viewModel: DoctorDashboardViewModel = viewModel(),
    onLogout: () -> Unit
) {
    val doctorProfile by viewModel.doctorProfile.collectAsState()
    val appointments by viewModel.appointments.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAppointments()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Doctor Dashboard", fontWeight = FontWeight.Bold) },
                actions = {
                    TextButton(onClick = {
                        viewModel.logout()
                        onLogout()
                    }) {
                        Text("Logout", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Welcome, ${doctorProfile?.name ?: "Doctor"}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Here are your appointments:",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (appointments.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No appointments yet.", color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(appointments) { appointment ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Patient: ${appointment.patientName.takeIf { it.isNotBlank() } ?: "Unknown"}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Phone: ${appointment.patientPhone.takeIf { it.isNotBlank() } ?: "N/A"}")
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Date: ${appointment.date} at ${appointment.time}")
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Status: ${appointment.status}",
                                    color = if (appointment.status == "Upcoming") Color(0xFF4CAF50) else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
