import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.exxuslee.data"
    compileSdk = property("version.compileSdk").toString().toInt()

    defaultConfig {
        minSdk = property("version.minSdk").toString().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        
        buildConfigField("String", "CMC_API_KEY",
            "\"1dc26ec9-8c92-42d2-98b1-3bd47c1f6d85," +
                    "71c1b15e-aa6f-4e7a-8957-4311dee99de0," +
                    "16a371a3-ab93-48f4-b419-7832f08585e4," +
                    "b55bddf6-674f-4a05-8c55-95ece68d0cec," +
                    "67279cb8-5f8a-41f1-8246-e0379bf4476a," +
                    "390d14e3-e426-4438-bd57-9ec69a0f09de," +
                    "e7eef857-e37c-45d2-b386-18813f7fcce9," +
                    "74f5e6c9-c8df-4555-8b74-86cb445e0041," +
                    "6c4ba1b1-8ef5-4cba-beca-017e6bfb4c79," +
                    "58022586-8eb3-4fe3-865d-46aff1d687a4," +
                    "a743b974-ab6d-4e3a-8ea6-0a2c4d596299," +
                    "1d3992fd-9a70-4bd7-80c4-22e51d1e064e," +
                    "e37aed32-c930-4a0b-831b-e4ca4d8a42d6," +
                    "29677e3e-4c17-4df6-aaa4-b6a602f68156," +
                    "a03aac51-32e7-4681-ab26-888e0f4c3264," +
                    "999909b9-e0bd-4b40-ba90-40c169db0d7d," +
                    "a364a97a-1215-4a6a-b467-f451e3cfdd9e," +
                    "d8ce0c15-12ae-4765-9d9d-f39c28b3629a," +
                    "11071dc5-cef1-4098-8044-a937dcbbf907," +
                    "d21a5fd7-9245-4bfb-bbef-a4b1836d74fa," +
                    "8e5af508-062f-425a-86e9-6a04bdeedbba," +
                    "4f2efa52-e5f4-4016-9da1-8b041c45435b," +
                    "79f126e1-9da5-434b-8136-6f48c083a8b1," +
                    "8ca4be76-d6e6-4839-8684-e9056c0eb752," +
                    "1c9f1dfe-ec4a-4ba3-bcf6-f4df9fc8f7ad," +
                    "af956cf7-652e-4a2f-a672-9514f7540d17," +
                    "6a96ced1-864c-4b08-b849-64bfaf4558ba," +
                    "985c8882-d750-4b70-943e-fc5826eabc75," +
                    "28c7ebbb-1ac9-4bb4-ab8d-3b544e5751d5," +
                    "8f1a5db2-79dc-44fd-a0e7-91507b37e8f0," +
                    "a9bc7f81-af5a-4c37-9f47-acb630f37b21," +
                    "cc5a2437-7e18-41ac-a8de-fcb5cfd17bd6," +
                    "513060cc-509b-4767-87d0-c948ca954c01," +
                    "9f9ea328-de14-4933-97dd-1d78791a9cff," +
                    "70f18ab5-a072-4fc9-8ebe-5ded040a2aaa," +
                    "567c1029-6072-4da4-9282-8ab452094be4," +
                    "ebcff229-cd18-485b-a02d-f418bcd541a0," +
                    "f43be964-1474-412c-8fb0-7cfd86db086e," +
                    "3aabd76a-b2ce-44e8-a809-04b5ef3a7c22," +
                    "2610aabc-d0af-4ecb-870d-cbc50e201620," +
                    "d0edf54c-1233-4821-b098-b5e47ddc400f," +
                    "6250c2bc-25cf-42b1-92a2-acb07989c4bf," +
                    "10bf200d-152f-49a7-b0bb-26659b158f08," +
                    "640bbefd-e318-4107-826e-88d798b741b4\"")
    }

    testOptions {
        targetSdk = property("version.targetSdk").toString().toInt()
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(fileTree("libs") { include("*.jar") })
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)

    implementation(libs.javax.annotation)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.koin.android)
    implementation(libs.koin.navigation)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.content.encoding)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)

    implementation(libs.kotlinx.coroutines.android)

    testImplementation(project(":data"))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.android.arch.core.testing)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.androidx.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.arch.core.testing)
    androidTestImplementation(libs.kotlinx.coroutines.test)

}