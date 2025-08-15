# Movio - Your Ultimate Entertainment Companion

<div align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green.svg" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Kotlin-blue.svg" alt="Language">
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-orange.svg" alt="UI">
  <img src="https://img.shields.io/badge/Architecture-MVVM-red.svg" alt="Architecture">
  <img src="https://img.shields.io/badge/DI-Hilt-yellow.svg" alt="DI">
</div>

A modern Android application built with Jetpack Compose for browsing and watching series content. The app features a clean Material Design interface with detailed series information, seasons, and episodes.

## 🚀 Features

### 🎯 **Smart Movie Discovery**
- **AI-Powered Content Detection**: Advanced image analysis to identify movies from screenshots or posters
- **Personalized Recommendations**: Intelligent suggestion engine based on viewing history and preferences
- **Episode Trailers**: Seamless trailer integration for TV shows and movie series

### 🔍 **Enhanced Search & Browse**
- **Real-time Search**: Lightning-fast movie and TV show search with auto-suggestions
- **Trending Content**: Stay updated with what's popular worldwide

### 📱 **Modern User Experience**
- **Jetpack Compose UI**: Fluid, responsive interface
- **Dark/Light Theme**: Automatic theme switching based on system preferences
- **Offline Support**: Browse previously viewed content without internet connection
- **Localization Support**: Multi-language experience with language selection based on user preferences

### 🔐 **Robust Backend Integration**
- **Firebase Analytics**: Comprehensive user behavior tracking
- **Crash Reporting**: Real-time crash monitoring with Firebase Crashlytics

### 🏗️ **Enterprise-Grade Architecture**
- **Clean Architecture**: Separation of concerns with Domain-Data-Presentation layers
- **Dependency Injection**: Hilt-powered DI for testable, maintainable code
- **Modular Design**: Feature-based modules for scalability
## 🤖 AI/ML Features

The app includes intelligent content detection capabilities:

### Image Content Analysis
- **Scene Detection**: Automatically detect and categorize scene types
- **Content Rating**: AI-powered content appropriateness detection
- **Image Quality Assessment**: Analyze image quality for optimal display
- **Auto-tagging**: Generate relevant tags for series posters and scenes

## 🛠️ Tech Stack

- **Language**: Kotlin
- **Android SDK**
- **Work Manager**
- **Paging 3**
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Dependency Injection**: Hilt (Dagger)
- **Build System**: Gradle
- **Navigation**: Compose Navigation
- **Networking**: Retrofit + OkHttp
- **Image Loading**: Coil
- **Async Programming**: Coroutines + Flow
- **Database**: Room
- **Testing**: JUnit, MockK, Truth
- **Content Detection**: ML Kit / TensorFlow Lite
## 🏗️ App Architecture
The app follows **Clean Architecture** principles with **MVVM** pattern and **Modularization** for robust, scalable, and testable code:

### Modular Architecture (By Presentation/Data/Domain)
<img width="1028" height="495" alt="simple" src="https://github.com/user-attachments/assets/7ef5ce50-0298-4800-a56f-905272f5c789" />


### 📦 Module Overview

The app is structured using a **multi-module architecture** to promote separation of concerns, improve build performance, and enable better team collaboration. Each module has specific responsibilities:

### 🎯 Core Modules

#### `:app` - Main Application Module
**Responsibilities:**
- Application entry point and initialization
- Hilt dependency injection setup
- Activity hosting for navigation
- App-level configuration and permissions
#### `:presentation` - UI Layer Module
**Responsibilities:**
- Jetpack Compose UI screens and components
- MVVM ViewModels for state management
- Navigation between screens
- UI state handling and user interactions
- Screen-specific business logic
#### `:domain` - Business Logic Module
**Responsibilities:**
- Pure business logic implementation
- Use cases for complex operations
- Domain models and entities
- Repository interfaces (contracts)
- Business rules and validations
#### `:data`
**Responsibilities:**
- Repository pattern implementation
- API integration with Retrofit
- Local database with Room
- Data transformation and mapping
- Caching strategies and offline support
#### `:designSystem` - UI Design System Module
**Responsibilities:**
- Consistent UI component library
- Reusable Compose components
- Design tokens and guidelines
- Cross-module UI consistency
#### `:detectImageContent` - AI/ML Content Analysis Module
**Responsibilities:**
- **Image Content Analysis**: Automatically analyze series posters and episode images
- **Scene Detection**: Classify scenes (action, drama, comedy, etc.) for better categorization
- **Content Rating**: AI-powered content appropriateness detection for family-friendly filtering
## 📋 Prerequisites

- Android Studio Narwhal (2025.1.2) or later
- JDK 11 or higher
- Android SDK 24 or higher
- Git
## 🔧 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yasserahmed10/movio-android.git
cd movio-android
```
### 2. Create Configuration Files
Create a secrets.properties file in the root directory:
```bash
# API Configuration
API_BASE_URL="your_api_base_url"
API_KEY="your_api_key"
```
### 3. Add Local Properties (if needed)
Update local.properties with your Android SDK path:
```bash
sdk.dir=/path/to/your/Android/Sdk
```
### 4. Build and Run
1. Open the project in Android Studio  
2. Sync the project with Gradle files  
3. Build the project (**Build > Make Project**)  
4. Run on device or emulator (**Run > Run 'app'**)
### 5.📱 Running the App
1. Enable USB debugging and connect your physical device  
2. Create an Android Virtual Device (AVD) with API level 24 or higher  
3. Choose between `debug` and `release` builds from the Build Variants panel
## 📸 Screenshots
<table style="width: 100%; border-collapse: collapse;"><tbody><tr><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Onboarding</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Splash</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Login</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Home</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Search</th></tr><tr><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Onboarding" src="https://github.com/user-attachments/assets/f0c64048-b3f8-4643-a07d-2ed8c1b81795"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Splash" src="https://github.com/user-attachments/assets/b44255bc-251b-48be-b2a3-1632734d45ce"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Login" src="https://github.com/user-attachments/assets/3cfceebc-236b-4545-b5b0-ebbae324a15b"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Home" src="https://github.com/user-attachments/assets/1c14aad2-a1a1-4439-9a06-df27321e3fd9"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Search" src="https://github.com/user-attachments/assets/bd0e97d7-2e0c-4332-a162-c7b2d5ad3e60"></td></tr><tr><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Library</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Profile</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Movie details</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Submit rating</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Sharing</th></tr><tr><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Library" src="https://github.com/user-attachments/assets/f0b33bba-28c8-420e-9d9d-78e26e1dbc7e"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Profile" src="https://github.com/user-attachments/assets/12e2ecfc-008a-44ed-926b-1e8faf269925"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Movie details" src="https://github.com/user-attachments/assets/5c206375-46ac-48ac-9f0b-48ee720ed04c"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Submit rating" src="https://github.com/user-attachments/assets/a6e7eb47-c99e-4792-a5d7-7728583aa99b"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Sharing" src="https://github.com/user-attachments/assets/f6785279-0c49-4dfb-a181-da9aa75059d0"></td></tr><tr><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Actor details</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">loading data</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Current seasons</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">My ratings</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Reviews</th></tr><tr><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Actor details" src="https://github.com/user-attachments/assets/9a058767-7422-4415-9e76-4ad0a63b6af6"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="loading data" src="https://github.com/user-attachments/assets/5160b705-46e8-4175-be7b-5c7c51bc9cfe"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Current seasons" src="https://github.com/user-attachments/assets/0cded1f8-fa9d-4497-9c2c-c7ed041472c3"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="My ratings" src="https://github.com/user-attachments/assets/9a3027e0-cead-4168-bb6c-203e1cacf16b"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Reviews" src="https://github.com/user-attachments/assets/5679b018-5c22-4d8a-a3fb-c00bd08a2d8d"></td></tr><tr><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Series details</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Similer series</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Top cast</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Supporting arabic language</th><th style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;">Light theme</th></tr><tr><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Series details" src="https://github.com/user-attachments/assets/1d02fad8-8e57-4d52-8d4f-34ee964af1e0"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Similer series" src="https://github.com/user-attachments/assets/505c4b3b-ebee-49f8-9077-8ee3e34a6568"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Top cast" src="https://github.com/user-attachments/assets/56212013-06b4-47d0-b9d6-f0713636c359"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Supporting arabic language" src="https://github.com/user-attachments/assets/bea47d56-da8d-497f-9fa1-67b6e25aa1b7"></td><td style="width: 20%; text-align: center; border: 1px solid #ccc; padding: 8px;"><img style="max-width: 100%; height: auto;" alt="Light theme" src="https://github.com/user-attachments/assets/f52e3c69-4e9d-4dc0-8b1f-e8dddcbf3451"></td></tr></tbody></table>
