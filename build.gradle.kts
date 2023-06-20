buildscript {
    extra.apply {
        set("detektVersion", "1.22.0")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")
        classpath(
            "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${rootProject.extra["detektVersion"]}",
        )
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt) apply true
    alias(libs.plugins.androidTest) apply false
    alias(libs.plugins.androidx.baselineprofile) apply false
}

allprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        config = files("${project.rootDir}/detekt/config.yml")
        baseline = file("${project.rootDir}/detekt/baseline.xml")

        autoCorrect = true
        parallel = true
    }

    dependencies {
        detekt(
            "io.gitlab.arturbosch.detekt:detekt-formatting:${rootProject.extra["detektVersion"]}",
        )
        detekt("io.gitlab.arturbosch.detekt:detekt-cli:${rootProject.extra["detektVersion"]}")
    }
}

allprojects {
    configurations {
        all {
            resolutionStrategy {
                force(rootProject.project.libs.okhttp)
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
