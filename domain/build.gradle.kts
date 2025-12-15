import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.exxlexxlee.domain"
    compileSdk = property("version.compileSdk").toString().toInt()

    defaultConfig {
        minSdk = property("version.minSdk").toString().toInt()
    }

    testOptions {
        targetSdk = property("version.targetSdk").toString().toInt()
    }

    buildTypes {
        create("mock") {
            initWith(getByName("debug"))
        }
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

}

dependencies {
    implementation(libs.koin.android)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)
}