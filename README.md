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

- **Series Details**: Comprehensive information about TV series
- **Season Management**: Browse through different seasons
- **Episode Listings**: Detailed episode information
- **Modern UI**: Built with Jetpack Compose and Material Design
- **Clean Architecture**: MVVM pattern with Hilt dependency injection
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
- **work Manager**
- **Pagination**
- **Firebase**: Performance, Analytics, and Crashlytics
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
