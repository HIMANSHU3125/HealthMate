package com.example.loginscreen.feature.LoginPAge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loginscreen.R
import com.example.loginscreen.ui.viewmodel.AuthStatus
import com.example.loginscreen.ui.viewmodel.AuthViewModel

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

@Composable
fun loginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("patient") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isSignUp by remember { mutableStateOf(false) }
    
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val authStatus by authViewModel.authStatus.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()

    LaunchedEffect(authStatus) {
        if (authStatus is AuthStatus.Success) {
            val userRole = (authStatus as AuthStatus.Success).role
            onLoginSuccess(userRole)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.gray))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(R.drawable.doctor_login),
            contentDescription = null,
            modifier = Modifier.size(160.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = if (isSignUp) "Create Account" else "Welcome Back", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(7.dp))

        Text(text = if (isSignUp) "Sign up to continue" else "Sign in to continue", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                if (isSignUp) {
                    Text(text = "Name", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Enter name") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Phone", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Enter phone") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Role", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = role == "patient", onClick = { role = "patient" })
                        Text("Patient")
                        Spacer(modifier = Modifier.width(16.dp))
                        RadioButton(selected = role == "doctor", onClick = { role = "doctor" })
                        Text("Doctor")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    if (role == "doctor") {
                        Text(text = "Profile Image", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(onClick = { launcher.launch("image/*") }) {
                                Text("Select Image")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            if (imageUri != null) {
                                Text("Image Selected", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                            } else {
                                Text("No image selected", color = Color.Gray)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                Text(text = "Email", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Enter email") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Password", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Enter Password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null)
                    },
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (authStatus is AuthStatus.Error) {
                    Text(
                        text = (authStatus as AuthStatus.Error).message,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (!isSignUp) {
                    Text(text = "Forget Password ?", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Button(
                onClick = {
                    if (isSignUp) {
                        authViewModel.signup(email, password, name, phone, role, imageUri)
                    } else {
                        authViewModel.login(email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.purple)
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        text = if (isSignUp) "Sign Up" else "Login",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Text(
                text = if (isSignUp) "Already have an account? Login" else "Don't have an account? Sign up",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.purple),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { isSignUp = !isSignUp }
            )
        }
    }
}