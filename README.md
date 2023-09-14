[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)]()
[![Kotlin](https://img.shields.io/badge/Kotlin-1.8.21-green.svg)]()
[![Compose](https://img.shields.io/badge/Compose-1.4.7-green.svg)]()

# Overview

**CodeWars Challenge Viewer** is an Android app built entirely with Kotlin and Jetpack Compose that utilizes the CodeWars API. It follows Android design and development best practices, such as the Clean Architecture Principle. The application consists of two screens:
- A screen showing a list of completed challenges
- A screen showing a detail view of a challenge that will be selected from the previous list screen

## Screenshots

<img src="https://github.com/LStudioO/CodeWarsChallengeViewer/assets/20700372/39876ff1-42d4-4358-93df-b84ca1121758" width="270" height="570">
<img src="https://github.com/LStudioO/CodeWarsChallengeViewer/assets/20700372/515c53cc-1e63-485c-9855-75a70940da04" width="270" height="570">
<img src="https://github.com/LStudioO/CodeWarsChallengeViewer/assets/20700372/d24a5ba5-b8c8-42ca-9b28-91217b87be0e" width="270" height="570">

## Installation

You can build the project with Android Studio Flamingo+ or [download the APK directly](https://github.com/LStudioO/CodeWarsChallengeViewer/releases) from the releases.

## Setup

The app is build for [`colbydauph`](https://www.codewars.com/users/colbydauph). If you want to change the user, go to [`InMemoryUserDataSource`](feature/user/src/main/java/com/feature/user/data/local/data_source/InMemoryUserDataSource.kt) and modify the id accordingly.

## Project Module Structure
```
└── Project
    ├── app
    ├── benchmark
    ├── core
    │   └── ui
    │   └── utils
    │       └── kotlin
    │       └── platform
    │       └── testing
    ├── feature
    │   └── challenge-details
    │   └── user
    └──
```

## Dependencies
### App
- [Koin](https://insert-koin.io/) - The pragmatic Kotlin & Kotlin Multiplatform Dependency Injection framework.
- [Android Jetpack](https://developer.android.com/jetpack) 
  - [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android’s recommended modern toolkit for building native UI.
  - [Navigation](https://developer.android.com/jetpack/compose/navigation) - You can navigate between composables while taking advantage of the Navigation component’s infrastructure and features.
  - [Material Design 3](https://developer.android.com/jetpack/androidx/releases/compose-material3) - Build Jetpack Compose UIs with Material Design 3 Components.
  - [Android KTX](https://developer.android.com/kotlin/ktx.html) - Provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
  - [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - The Paging library helps you load and display pages of data from a larger dataset from local storage or over network.
  - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) -  A concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Android, Java and Kotlin. It makes it easy to parse JSON into Java and Kotlin classes.
- [Coil](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines.
- [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API which provides utility on top of Android's normal Log class.
- [Accompanist](https://github.com/google/accompanist) - A group of libraries that aim to supplement Jetpack Compose with features that are commonly required by developers but not yet available.
- [Rich-Text](https://github.com/halilozercan/compose-richtext) - A collection of Compose libraries for working with rich text formatting and documents.
- [KotlinX DateTime](https://github.com/Kotlin/kotlinx-datetime) - A multiplatform Kotlin library for working with date and time.
- [KotlinX Immutable](https://github.com/Kotlin/kotlinx.collections.immutable) - Immutable collection interfaces and implementation prototypes for Kotlin.

### Testing
- [Mockk](https://mockk.io/) - A mocking library for Kotlin.
- [Espresso](https://developer.android.com/training/testing/espresso) - One of the popular Android App Testing Frameworks.
- [JUnit4](https://github.com/junit-team/junit4) - A simple framework to write repeatable tests.
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - A scriptable web server for testing HTTP clients.
- [ProfileInstaller](https://developer.android.com/jetpack/androidx/releases/profileinstaller) - Enables libraries to prepopulate ahead of time compilation traces to be read by ART.
- [UI Automator](https://developer.android.com/training/testing/other-components/ui-automator) - A UI testing framework suitable for cross-app functional UI testing across system and installed apps.

## Performance

### Benchmarks

Find all tests written using [`Macrobenchmark`](https://developer.android.com/topic/performance/benchmarking/macrobenchmark-overview)
in the `benchmarks` module. This module also contains the test to generate the Baseline profile.

### Baseline profiles

The baseline profile for this app is located at [`app/src/main/baseline-prof.txt`](app/src/main/baseline-prof.txt).
It contains rules that enable AOT compilation of the critical user path taken during app launch.

To generate the baseline profile, run the
`BaselineProfileGenerator` benchmark test on an AOSP Android Emulator.
Then copy the resulting baseline profile from the emulator to [`app/src/main/baseline-prof.txt`](app/src/main/baseline-prof.txt).

## Demo

https://github.com/LStudioO/CodeWarsChallengeViewer/assets/20700372/75f275cb-8d4e-4ec7-b9e8-6b1390e20201

# License

**CodeWars Challenge Viewer** is distributed under the terms of the Apache License (Version 2.0). See the
[license](LICENSE) for more information.
