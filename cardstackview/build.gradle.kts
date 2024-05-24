plugins {
    alias(libs.plugins.androidLibrary)
}

android {
    namespace = "com.yuyakaido.android.cardstackview"
    compileSdk = 34

    defaultConfig {
        minSdk = 19
    }

    buildTypes {
        release {
            isDefault = true
            isJniDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.material)
}