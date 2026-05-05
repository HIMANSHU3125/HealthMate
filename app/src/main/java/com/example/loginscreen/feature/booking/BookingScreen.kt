package com.example.loginscreen.feature.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loginscreen.R
import com.example.loginscreen.ui.viewmodel.BookingStatus
import com.example.loginscreen.ui.viewmodel.BookingViewModel

@Composable
fun BookingScreen(
    doctorId: String,
    doctorName: String,
    onBack: () -> Unit,
    onBookingSuccess: () -> Unit,
    bookingViewModel: BookingViewModel = viewModel(),
    userViewModel: com.example.loginscreen.ui.viewmodel.UserViewModel = viewModel()
) {
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val bookingStatus by bookingViewModel.bookingStatus.collectAsState()
    val isBooking by bookingViewModel.isBooking.collectAsState()
    val userProfile by userViewModel.userProfile.collectAsState()

    LaunchedEffect(bookingStatus) {
        if (bookingStatus is BookingStatus.Success) {
            onBookingSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.gray))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Book Appointment",
            fontSize =  24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(10.dp))
        
        Text(
            text = "With $doctorName",
            fontSize = 18.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(text = "Date (e.g. 2026-10-15)", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Select Date") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Time (e.g. 10:00 AM)", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Select Time") }
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (bookingStatus is BookingStatus.Error) {
                    Text(
                        text = (bookingStatus as BookingStatus.Error).message,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        val pName = userProfile?.name ?: ""
                        val pPhone = userProfile?.phone ?: ""
                        bookingViewModel.bookAppointment(doctorId, doctorName, pName, pPhone, date, time)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.purple)
                    ),
                    enabled = !isBooking && date.isNotBlank() && time.isNotBlank()
                ) {
                    if (isBooking) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            text = "Confirm Booking",
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        }
    }
}
