import java.io.FileInputStream
import java.util.Properties

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

    signingConfigs {
        val properties = Properties().apply {
            load(FileInputStream("${rootDir}/local.properties"))
        }

        create("release") {
            storeFile = file("../keystore/goodIdeaKey.jks")
            keyAlias = "${properties["keyAlias"]}"
            storePassword = "${properties["storePassword"]}"
            keyPassword = "${properties["keyPassword"]}"
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }

        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
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

    // Timber, https://github.com/JakeWharton/timber
    implementation(libs.timber)

    // Lottie, https://github.com/airbnb/lottie-android/releases
    implementation (libs.lottie)

    // Room Database
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)

    // Hilt
    implementation (libs.hilt.android)
    ksp (libs.hilt.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Moshi, https://github.com/square/moshi
    implementation (libs.moshi)
    implementation (libs.moshi.kotlin)
}