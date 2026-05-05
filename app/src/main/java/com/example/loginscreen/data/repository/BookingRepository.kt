package com.example.loginscreen.data.repository

import com.example.loginscreen.data.model.Appointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class  BookingRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun bookAppointment(
        doctorId: String,
        doctorName: String,
        patientName: String,
        patientPhone: String,
        date: String,
        time: String
    ): Result<Boolean> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("User not logged in"))
            val appointmentId = java.util.UUID.randomUUID().toString()

            val appointment = Appointment(
                id = appointmentId,
                doctorId = doctorId,
                doctorName = doctorName,
                userId = userId,
                patientName = patientName,
                patientPhone = patientPhone,
                date = date,
                time = time,
                status = "Upcoming"
            )

            firestore.collection("Appointments")
                .document(appointmentId)
                .set(appointment)
                .await()

            // Update user document
            firestore.collection("users")
                .document(userId)
                .update("appointments", com.google.firebase.firestore.FieldValue.arrayUnion(appointmentId))
                .await()

            // Update doctor document
            firestore.collection("Doctors")
                .document(doctorId)
                .update("appointments", com.google.firebase.firestore.FieldValue.arrayUnion(appointmentId))
                .await()

            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getDoctorAppointmentsFlow(doctorId: String): kotlinx.coroutines.flow.Flow<List<Appointment>> = kotlinx.coroutines.flow.callbackFlow {
        val listener = firestore.collection("Appointments")
            .whereEqualTo("doctorId", doctorId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val appointments = snapshot.toObjects(Appointment::class.java)
                    trySend(appointments)
                } else {
                    trySend(emptyList())
                }
            }
        awaitClose { listener.remove() }
    }

    suspend fun getUserAppointments(): Result<List<Appointment>> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("User not logged in"))
            val snapshot = firestore.collection("Appointments")
                .whereEqualTo("userId", userId)
                .get()
                .await()
            val appointments = snapshot.toObjects(Appointment::class.java)
            Result.success(appointments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
