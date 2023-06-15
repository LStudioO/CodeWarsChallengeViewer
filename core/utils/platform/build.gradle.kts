@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.core.utils.platform"
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
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.bundles.retrofit)
    implementation(libs.moshi.core)
    implementation(libs.timber)
    implementation(project(":core:utils:kotlin"))

    testImplementation(libs.bundles.test.common)

    androidTestImplementation(libs.bundles.test.android)
}
