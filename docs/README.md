# Creating a Kotlin App to Follow the World Cup

Project developed during the Santander Bootcamp 2023 - Mobile Android with Kotlin, under the guidance of experts [Pedro Silva](https://github.com), [Ezequiel Messore](https://github.com), [Igor Rotondo Bagliotti](https://github.com), and [Venilton FalvoJr](https://github.com).

This repository contains a native Android application built with clean architecture principles to track match schedules and manage push notifications using WorkManager. It also includes an isolated web interface prototype mapping the same workflows.

## Features

- **Match Schedule API**: Fetches live match schedules and details from a public JSON API endpoint.
- **Local Push Notifications**: Automated match reminders scheduled via WorkManager.
- **Clean Architecture & Modules**: Modularized codebase splitting concerns across App, Data, Domain, and Notification Scheduler layers.
- **Jetpack Compose UI**: Modern declarative UI design using Material 3 guidelines.
- **Multi-language Support**: Full support for English (EN-US), Portuguese (PT-BR), and Spanish (ES).
- **Web Interface Standalone**: An accessible, responsive HTML/JS interface prototype that mimics the mobile application core business logic.

## Project Architecture & Modules

The Android codebase is structured into highly cohesive and decoupled feature modules:

- **`:app`**: Application entrypoint, Dependency Injection configuration, ViewModels, and Jetpack Compose screens (`MainScreen.kt`).
- **`:domain`**: Pure business logic containing models and core use cases:
  - `GetMatchesUseCase.kt` (Fetch and filter schedules)
  - `EnableNotificationUseCase.kt` (Register match reminder)
  - `DisableNotificationUseCase.kt` (Cancel match reminder)
- **`:data`**: Outlines repository implementations and orchestrates two distinct data sources:
  - `local`: SQLite persistence abstraction layer using **Room Database**.
  - `remote`: REST API HTTP client implementation powered by **Retrofit**.
- **`:notification-scheduler`**: Feature module handling persistent background scheduling using **WorkManager** to trigger push notifications right before matches start.

## Tech Stack & Production Dependencies

- **Language**: Kotlin (Coroutines, KTX)
- **UI Framework**: Jetpack Compose (Material 3, Coil Compose for image caching)
- **Architecture**: MVI / MVVM Pattern with Jetpack Lifecycle ViewModels
- **Dependency Injection**: Dagger Hilt
- **Local Storage**: Room Database
- **Networking**: Retrofit + Moshi / OkHttp
- **Background Processing**: WorkManager

## Companion Web Prototype Stack
- **HTML5 & CSS3**: Responsive markup framework with native CSS variables for theme states.
- **JavaScript & Web Share**: Front-end logic managing mock UI strings, theme persistence, and local mock states via `localStorage`.

## How to Run & Setup

### 1. Android Application (Native Production)
1. Open Android Studio (Flamingo or later).
2. Import this project using the local Gradle wrapper.
3. Allow the project to sync and fetch all required dependencies.
4. Select an active emulator or physical device (API 21+) and run the **`app`** module.
*(Note: Android 13+ requires accepting the explicit `POST_NOTIFICATIONS` runtime prompt to fire local push notifications).*

### 2. Web Interface Demo (Standalone)
1. Add required stadium images inside a root `/assets` folder matching the filenames defined in `script.js`.
2. Open `index.html` directly in any modern web browser.

## Testing & Quality Assurance

- **Unit Tests**: Coverage for use cases, data mapping, and repository logic using JUnit4 and MockK.
- **WorkManager Testing**: Validated execution delays using `androidx.work:work-testing`.
- **UI Tests**: Isolated Compose component testing via `androidx.compose.ui:ui-test-junit4`.

![Project Screenshot](assets/screenshot_web_demo.png)

See [original repository](https://github.com/digitalinnovationone/copa-2022-android/tree/feature/base-project).

[LICENSE](/LICENSE)
