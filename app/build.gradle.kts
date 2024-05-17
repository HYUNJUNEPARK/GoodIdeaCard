plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.aos.goodideacard"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aos.goodideacard"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
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
}