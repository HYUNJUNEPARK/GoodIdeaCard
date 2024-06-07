plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.aos.goodideacard"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aos.goodideacard"
        minSdk = 26
        targetSdk = 34
        versionCode = 240000
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false //proguard
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }

        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug") //TODO Change signingConfig
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        flavorDimensions += "version"
        viewBinding = true
        buildConfig = true
    }

    productFlavors {
        create("dev") {
            dimension = "version"
            manifestPlaceholders += mapOf("appName" to "@string/app_name_dev")
            applicationIdSuffix = ".dev"
        }
        create("rel") {
            dimension = "version"
            manifestPlaceholders += mapOf("appName" to "@string/app_name_rel")
            applicationIdSuffix = ".rel"
        }
        create("prod") {
            dimension = "version"
            manifestPlaceholders += mapOf("appName" to "@string/app_name")
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Module
    implementation(project(":cardstackview")) //custom cardstackview cf. origin:https://github.com/yuyakaido/CardStackView

    // Timber
    implementation(libs.timber) //https://github.com/JakeWharton/timber

    // Lottie
    implementation (libs.lottie) //https://github.com/airbnb/lottie-android/releases

    // Room Database
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)

    // Hilt
    implementation (libs.hilt.android)
    ksp (libs.hilt.compiler)

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")
}