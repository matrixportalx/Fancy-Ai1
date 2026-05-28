plugins {
    alias(libs.plugins.android.application)
}

extensions.configure<com.android.build.api.dsl.ApplicationExtension> {
    namespace = "com.mrj.fancyai"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mrj.fancyai"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "3.0.4"
        ndkVersion = "27.2.12479018"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            abiFilters.addAll(listOf("arm64-v8a", "armeabi-v7a"))
        }
        packagingOptions {
            resources {
                pickFirsts += setOf("lib/arm64-v8a/libc++_shared.so", "lib/armeabi-v7a/libc++_shared.so")
            }
        }
    }

    packaging {
        resources {
            pickFirsts += setOf("lib/arm64-v8a/libc++_shared.so", "lib/armeabi-v7a/libc++_shared.so")
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.webkit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
