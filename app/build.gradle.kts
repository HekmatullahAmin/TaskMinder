plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.0-1.0.24"
    id("com.google.dagger.hilt.android") version "2.52"
    id("com.google.gms.google-services")
    // Add the dependency for the Performance Monitoring Gradle plugin
    id("com.google.firebase.firebase-perf")
    // Add the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.hekmatullahamin.taskminder"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hekmatullahamin.taskminder"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.hekmatullahamin.taskminder.CustomTestRunner"
        // Replace com.example.android.dagger with your class path.
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.compose.material3)
    implementation(libs.firebase.crashlytics.buildtools)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//    hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

//    Firebase
    implementation(platform(libs.firebase.bom))
    // Add the dependencies for the Crashlytics and Analytics libraries
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    // Add the dependency for the Firebase Authentication library
    implementation(libs.firebase.auth)
    // Declare the dependency for the Cloud Firestore library
    implementation(libs.firebase.firestore)
    // Add the dependency for the Performance Monitoring library
    implementation(libs.firebase.perf)
    // Add the dependency for the Remote Config library
    implementation(libs.firebase.config)
    //    Preferences DataStore
    implementation(libs.androidx.datastore.preferences)
    //    testing navigation
    androidTestImplementation(libs.androidx.navigation.testing)
    // For instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
}