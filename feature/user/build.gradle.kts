@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.feature.user"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
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
    implementation(libs.bundles.compose.debug)
    implementation(libs.navigation)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.navigation)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.moshi)
    implementation(libs.bundles.retrofit)
    ksp(libs.moshi.codegen)

    testImplementation(libs.bundles.test.common)

    androidTestImplementation(libs.bundles.test.android)

    implementation(project(":core:ui"))
    implementation(project(":core:utils:platform"))
    implementation(project(":core:utils:kotlin"))
}
