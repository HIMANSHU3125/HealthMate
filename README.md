# 🩺 HealthMate – Doctor Appointment Android App

HealthMate is a modern Android healthcare application that helps users find doctors, explore specialists, and book appointments easily. The app is built using **Kotlin + Jetpack Compose** and follows **MVVM architecture** with Firebase integration for authentication and real-time database.

---

## ✨ Features

### 👤 User Features
- Secure Login & Signup (Firebase Authentication)
- Browse doctors by specialization
- View doctor details & profiles
- Book doctor appointments
- Real-time data updates using Firebase Firestore
- Clean and modern UI using Jetpack Compose

### 👨‍⚕️ Doctor Module (Planned/Optional)
- Doctor profile management
- Appointment request management
- Patient details view

---

## 🧱 Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM (Model–View–ViewModel) |
| Backend | Firebase Authentication |
| Database | Firebase Firestore |
| Build System | Gradle (KTS) |

---

## 📱 App Architecture

The app follows MVVM architecture:

```
UI (Compose Screens)
        ↓
ViewModel (Business Logic)
        ↓
Repository (Data Handling)
        ↓
Firebase (Auth + Firestore)
```

This structure makes the app scalable, maintainable, and testable.

---

## 📂 Project Structure

```
app/
├── ui/          → Compose screens & components
├── viewmodel/   → ViewModels
├── repository/  → Firebase data handling
├── model/       → Data models
├── navigation/  → Navigation routes
└── utils/       → Helper classes
```

---

## 🔐 Firebase Integration

The app uses Firebase for:
- User Authentication (Email/Password)
- Cloud Firestore Database
- Real-time data syncing

> To run this project, you must connect your own Firebase project.

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/HIMANSHU3125/HealthMate.git
```

### 2️⃣ Open in Android Studio
- Open Android Studio
- Click **Open Project**
- Select the cloned folder

### 3️⃣ Connect Firebase
- Go to [Firebase Console](https://console.firebase.google.com/)
- Create a new project
- Add Android app with your package name
- Download `google-services.json`
- Paste it inside: `app/google-services.json`

### 4️⃣ Run the App
Click ▶ **Run** in Android Studio.

---

## 📸 Screenshots

<p align="center">
  <img src="WhatsApp Image 2026-05-05 at 15.44.58.jpeg" width="180" alt="Screen 1"/>
  <img src="WhatsApp Image 2026-05-05 at 15.44.59.jpeg" width="180" alt="Screen 2"/>
  <img src="WhatsApp Image 2026-05-05 at 15.44.59 (1).jpeg" width="180" alt="Screen 3"/>
  <img src="WhatsApp Image 2026-05-05 at 15.44.59 (2).jpeg" width="180" alt="Screen 4"/>
</p>

<p align="center">
  <img src="WhatsApp Image 2026-05-05 at 15.45.00.jpeg" width="180" alt="Screen 5"/>
  <img src="WhatsApp Image 2026-05-05 at 15.45.00 (1).jpeg" width="180" alt="Screen 6"/>
  <img src="WhatsApp Image 2026-05-05 at 15.45.00 (2).jpeg" width="180" alt="Screen 7"/>
</p>

---

## 🚀 Future Improvements

- Online payment integration
- Push notifications for appointments
- Doctor dashboard
- Dark mode support
- Video consultation feature

---

## 🤝 Contributing

Contributions are welcome! Feel free to fork this repo and submit a pull request.

---

## 👨‍💻 Developer

**Himanshu Gupta**
Android Developer (Kotlin + Jetpack Compose)

---

## ⭐ If you like this project

Give it a ⭐ on GitHub and share feedback!
