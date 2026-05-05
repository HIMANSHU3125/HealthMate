package com.example.loginscreen.core.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginscreen.core.model.CategoryModel
import com.example.loginscreen.core.model.DoctorModel
import com.example.loginscreen.data.repository.DoctorRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val doctorRepository = DoctorRepository()

    private val _category = MutableLiveData<List<CategoryModel>>(emptyList())
    val category: LiveData<List<CategoryModel>> = _category

    private val _doctors = MutableLiveData<List<DoctorModel>>(emptyList())
    val doctors: LiveData<List<DoctorModel>> = _doctors

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var categoryLoaded = false
    private var doctorsLoaded = false

    fun loadCategory(force: Boolean = false) {
        if (categoryLoaded && !force) return
        categoryLoaded = true

        viewModelScope.launch {
            val result = doctorRepository.getCategories()
            if (result.isSuccess) {
                _category.value = result.getOrNull() ?: emptyList()
            } else {
                categoryLoaded = false
                _error.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun loadDoctors(force: Boolean = false) {
        if (doctorsLoaded && !force) return
        doctorsLoaded = true

        viewModelScope.launch {
            val result = doctorRepository.getDoctors()
            if (result.isSuccess) {
                _doctors.value = result.getOrNull() ?: emptyList()
            } else {
                doctorsLoaded = false
                _error.value = result.exceptionOrNull()?.message
            }
        }
    }
}