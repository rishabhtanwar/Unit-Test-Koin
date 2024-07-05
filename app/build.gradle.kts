plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.currencyconverter"
    compileSdk = 34
    buildFeatures{
        buildConfig = true
    }
    defaultConfig {
        applicationId = "com.example.currencyconverter"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            val currencyApiKey: String by project
            val baseUrl: String by project
            isMinifyEnabled = false
            buildConfigField ("String", "API_KEY", "\"$currencyApiKey\"")
            buildConfigField ("String", "BASE_URL", "\"$baseUrl\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug{
            val currencyApiKey: String by project
            val baseUrl: String by project
            buildConfigField ("String", "API_KEY", "\"$currencyApiKey\"")
            buildConfigField ("String", "BASE_URL", "\"$baseUrl\"")
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    dataBinding {
        isEnabled = true
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
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.androidx.media3.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    /* Retrofit */
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    /* Ok HTTP */
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    //koin
    implementation ("io.insert-koin:koin-android:3.6.0-wasm-alpha2")
    implementation("io.insert-koin:koin-core:3.6.0-wasm-alpha2")

    //tests dependencies

    testImplementation ("junit:junit:4.13")
    androidTestImplementation ("androidx.test.ext:junit:1.1.1")
    androidTestImplementation ("androidx.test.ext:junit-ktx:1.1.1")
    androidTestImplementation ("androidx.test:core-ktx:1.5.0")
    androidTestImplementation ("androidx.test:rules:1.2.0")
    testImplementation ("org.mockito:mockito-core:3.3.3")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.4")
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    // Koin testing tools
    testImplementation("io.insert-koin:koin-test:3.6.0-wasm-alpha2")
    // Koin for JUnit 4
    testImplementation("io.insert-koin:koin-test-junit4:3.6.0-wasm-alpha2")
    // Koin for JUnit 5
    testImplementation("io.insert-koin:koin-test-junit5:3.6.0-wasm-alpha2")

    implementation ("androidx.media3:media3-exoplayer:1.3.1")
    implementation ("androidx.media3:media3-ui:1.3.1")
    implementation ("androidx.media3:media3-exoplayer-dash:1.3.1")

}