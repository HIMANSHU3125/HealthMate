package com.example.loginscreen.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginscreen.data.model.Appointment
import com.example.loginscreen.data.model.UserModel
import com.example.loginscreen.data.repository.AuthRepository
import com.example.loginscreen.data.repository.BookingRepository
import com.example.loginscreen.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DoctorDashboardViewModel : ViewModel() {
    private val bookingRepository = BookingRepository()
    private val userRepository = UserRepository()
    private val authRepository = AuthRepository()

    private val _doctorProfile = MutableStateFlow<UserModel?>(null)
    val doctorProfile: StateFlow<UserModel?> = _doctorProfile

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    init {
        viewModelScope.launch {
            userRepository.getUserProfileFlow().collectLatest { profile ->
                _doctorProfile.value = profile
            }
        }
    }

    fun fetchAppointments() {
        viewModelScope.launch {
            val doctorId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            bookingRepository.getDoctorAppointmentsFlow(doctorId).collectLatest { appointmentsList ->
                _appointments.value = appointmentsList
            }
        }
    }

    fun logout() {
        authRepository.logout()
    }
}
