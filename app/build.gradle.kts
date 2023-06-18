@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.vstorchevyi.codewars"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "com.vstorchevyi.codewars"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        versionCode = libs.versions.app.version.code.get().toInt()
        versionName = libs.versions.app.version.name.get()

        testInstrumentationRunner = "com.vstorchevyi.codewars.runner.AppTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    val composeBom = platform(libs.compose.bom)

    implementation(composeBom)
    implementation(libs.bundles.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.navigation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.retrofit)
    implementation(libs.timber)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.bundles.moshi)
    ksp(libs.moshi.codegen)

    testImplementation(libs.bundles.test.common)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.android.test)

    androidTestImplementation(composeBom)
    androidTestImplementation(libs.test.compose.ui)
    androidTestImplementation(libs.bundles.test.android)
    androidTestImplementation(libs.mockwebserver)
    androidTestImplementation(libs.okhttp)

    implementation(project(":core:ui"))
    implementation(project(":core:utils:kotlin"))
    implementation(project(":core:utils:platform"))
    implementation(project(":feature:user"))
}
