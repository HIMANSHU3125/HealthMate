package com.example.loginscreen.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signup(email: String, password: String, name: String, phone: String, role: String, imageUri: android.net.Uri? = null): Result<Boolean> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                var profileImageUrl = ""
                
                if (imageUri != null) {
                    val storageRef = com.google.firebase.storage.FirebaseStorage.getInstance().reference
                        .child("profile_images/${user.uid}.jpg")
                    storageRef.putFile(imageUri).await()
                    profileImageUrl = storageRef.downloadUrl.await().toString()
                }

                val userModel = com.example.loginscreen.data.model.UserModel(
                    uid = user.uid,
                    name = name,
                    email = email,
                    phone = phone,
                    role = role,
                    profileImage = profileImageUrl
                )
                
                val firestore = com.google.firebase.firestore.FirebaseFirestore.getInstance()
                
                // Save to users collection
                firestore.collection("users")
                    .document(user.uid)
                    .set(userModel)
                    .await()
                    
                // If role is doctor, save to Doctors collection
                if (role == "doctor") {
                    val doctorModel = com.example.loginscreen.core.model.DoctorModel(
                        Id = System.currentTimeMillis().toInt(), // Temporary ID logic, but document ID will be user.uid
                        Name = name,
                        Address = "Address not set", // You might want to update this later
                        Location = "",
                        Mobile = phone,
                        Patiens = "0",
                        Picture = profileImageUrl,
                        Rating = 0.0,
                        Site = "",
                        Special = "General", // Default specialty
                        Biography = "Biography not set",
                        Expriense = 0
                    )
                    
                    firestore.collection("Doctors")
                        .document(user.uid)
                        .set(doctorModel)
                        .await()
                }
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
