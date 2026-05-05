package com.example.loginscreen.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginscreen.data.repository.BookingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingViewModel : ViewModel() {
    private val bookingRepository = BookingRepository()

    private val _isBooking = MutableStateFlow(false)
    val isBooking: StateFlow<Boolean> = _isBooking

    private val _bookingStatus = MutableStateFlow<BookingStatus>(BookingStatus.Idle)
    val bookingStatus: StateFlow<BookingStatus> = _bookingStatus

    fun bookAppointment(doctorId: String, doctorName: String, patientName: String, patientPhone: String, date: String, time: String) {
        _isBooking.value = true
        _bookingStatus.value = BookingStatus.Idle
        viewModelScope.launch {
            val result = bookingRepository.bookAppointment(doctorId, doctorName, patientName, patientPhone, date, time)
            _isBooking.value = false
            if (result.isSuccess) {
                _bookingStatus.value = BookingStatus.Success
            } else {
                _bookingStatus.value = BookingStatus.Error(result.exceptionOrNull()?.message ?: "Booking failed")
            }
        }
    }
}

sealed class BookingStatus {
    object Idle : BookingStatus()
    object Success : BookingStatus()
    data class Error(val message: String) : BookingStatus()
}
