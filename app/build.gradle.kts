plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.facebook.react")
}

android {
    namespace = "com.example.cx_android"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.cx_android"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        jniLibs {
            useLegacyPackaging = true
            pickFirsts += setOf(
                "**/libc++_shared.so",
                "**/libfbjni.so",
                "**/libjsi.so",
                "**/libhermes.so"
            )
        }
    }
}

react {
    root.set(file("../react-native"))
    reactNativeDir.set(file("../react-native/node_modules/react-native"))
    cliFile.set(file("../react-native/node_modules/react-native/cli.js"))
    codegenDir.set(file("../react-native/node_modules/@react-native/codegen"))
    jsRootDir.set(file("../react-native"))
    bundleCommand.set("")  // Disable auto-bundling - we use pre-built bundle
    bundleAssetName.set("index.android.bundle")
    autolinkLibrariesWithApp()
}

// Disable JS bundling tasks - we use pre-built bundle from rnapp-bundle
tasks.configureEach {
    if (name.contains("bundle") && name.contains("JsAndAssets")) {
        enabled = false
    }
}

dependencies {
    // Existing Compose dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // AppCompat for Fragment support
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.fragment:fragment-ktx:1.8.5")

    // React Native
    implementation("com.facebook.react:react-android")
    implementation("com.facebook.react:hermes-android")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
