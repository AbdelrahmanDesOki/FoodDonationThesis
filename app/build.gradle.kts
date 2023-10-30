plugins {
    id("com.android.application")
    kotlin("android")
    id("io.realm.kotlin") version "1.8.0"
    id("com.google.gms.google-services")

}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.mongodb.app"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.compose.ui:ui:1.3.2")
    implementation("androidx.compose.ui:ui-tooling:1.3.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.2")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.3.2")
//    implementation("com.android.tools.build:gradle:7.0.0")

    implementation("androidx.compose.material3:material3:1.0.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")

    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("io.realm.kotlin:library-sync:1.8.0") // DON'T FORGET TO UPDATE VERSION IN PROJECT GRADLE


    implementation("com.google.maps.android:maps-compose:2.11.5")
    //google maps services
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    //google maps utils
    implementation("com.google.maps.android:android-maps-utils:3.4.0")
//   implementation(platform("androidx.compose:compose-bom:2023.03.00"))
//    implementation("androidx.compose.ui:ui-graphics")
//    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    debugImplementation("androidx.compose.ui:ui-test-manifest")

//    implementation ("androidx.compose.material2:material-icons-extended:1.0.0-beta01")
//    implementation ("androidx.compose.material:material:1.5.2")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:28.3.0"))
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx:20.2.1")


    implementation ("androidx.navigation:navigation-compose:2.5.3")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

    // coil for asyncImage
    implementation("io.coil-kt:coil-compose:2.3.0")
//    implementation("androidx.camera:camera-core:1.3.0")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")


//    implementation("androidx.navigation:navigation-compose:2.7.4")


}
