package com.example.loginscreen.data.repository

import com.example.loginscreen.core.model.CategoryModel
import com.example.loginscreen.core.model.DoctorModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DoctorRepository {
    private val firestore = FirebaseFirestore.getInstance()

    private val mockDoctors = listOf(
        DoctorModel(0, "Dr. Rajesh Kumar", "8502 Preston Rd, Inglewood, Maine 98380", "http://maps.google.com/maps?q=loc:31.995801008207952,44.31452133516133", "00123456789", "1200+", "android.resource://com.example.loginscreen/drawable/doctor_1", 4.2, "http://www.test.com", "Orthopedics", "A board-certified with over 25 years of experience, specializing in orthopedics. Known for patient-centered care and a commitment to the latest medical advancements.", 20),
        DoctorModel(1, "Dr. Priya Sharma", "8502 Preston Rd, Inglewood, Maine 98380", "http://maps.google.com/maps?q=loc:31.995801008207952,44.31452133516133", "00123456789", "500+", "android.resource://com.example.loginscreen/drawable/doctor_2", 4.5, "http://www.test.com", "Cardiology", "A board-certified with over 15 years of experience, specializing in heart conditions such as coronary artery disease and arrhythmias. Known for patient-centered care.", 4),
        DoctorModel(2, "Dr. Amit Patel", "8502 Preston Rd, Inglewood, Maine 98380", "http://maps.google.com/maps?q=loc:31.995801008207952,44.31452133516133", "00123456789", "500+", "android.resource://com.example.loginscreen/drawable/doctor_3", 4.2, "http://www.test.com", "Neurology", "A board-certified with over 15 years of experience, specializing in neurological disorders. Known for patient-centered care and a commitment to the latest medical advancements.", 6),
        DoctorModel(4, "Dr. Neha Gupta", "8502 Preston Rd, Inglewood, Maine 98380", "http://maps.google.com/maps?q=loc:31.995801008207952,44.31452133516133", "00123456789", "500+", "android.resource://com.example.loginscreen/drawable/doctor_4", 4.1, "http://www.test.com", "Radiology Specialist", "A board-certified with over 3 years of experience, specializing in radiology. Known for patient-centered care and a commitment to the latest medical advancements.", 3),
        DoctorModel(5, "Dr. Vikram Singh", "8502 Preston Rd, Inglewood, Maine 98380", "http://maps.google.com/maps?q=loc:31.995801008207952,44.31452133516133", "00123456789", "500+", "android.resource://com.example.loginscreen/drawable/doctor_5", 4.5, "http://www.test.com", "Dentistry", "A board-certified with over 20 years of experience, specializing in dentistry. Known for patient-centered care and a commitment to the latest medical advancements.", 20)
    )

    private val mockCategories = listOf(
        CategoryModel(0, "Cardiology", "https://img.icons8.com/color/96/000000/heart-with-pulse.png"),
        CategoryModel(1, "Dentistry", "https://img.icons8.com/color/96/000000/tooth.png"),
        CategoryModel(2, "Neurology", "https://img.icons8.com/color/96/000000/brain.png"),
        CategoryModel(3, "Orthopedics", "https://img.icons8.com/color/96/000000/bone.png"),
        CategoryModel(4, "Radiology", "https://img.icons8.com/color/96/000000/x-ray.png")
    )

    private suspend fun seedDataIfNeeded() {
        try {
            // Force upload this new data once so the user gets it in Firestore
            mockDoctors.forEach { doctor ->
                firestore.collection("Doctors").document(doctor.Id.toString()).set(doctor).await()
            }
            
            mockCategories.forEach { category ->
                firestore.collection("Category").document(category.Id.toString()).set(category).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getDoctors(): Result<List<DoctorModel>> {
        return try {
            seedDataIfNeeded()
            val snapshot = firestore.collection("Doctors").get().await()
            val doctors = snapshot.toObjects(DoctorModel::class.java)
            Result.success(doctors)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCategories(): Result<List<CategoryModel>> {
        return try {
            seedDataIfNeeded()
            val snapshot = firestore.collection("Category").get().await()
            val categories = snapshot.toObjects(CategoryModel::class.java)
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
