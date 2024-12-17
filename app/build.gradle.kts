plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.structure"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.structure"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
        dataBinding = true
        buildConfig = true
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


    // Dimension
    implementation(libs.ssp.android)
    implementation(libs.sdp.android)

    // CircularImageView
    implementation(libs.circleimageview)

    //Image Loading
    implementation(libs.fresco)

    // Cropping Image Selection
   // implementation (libs.ucrop)

    // Coroutine
    implementation(libs.kotlinx.coroutines.android)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.retrofit2.adapter.rxjava2)
    implementation(libs.converter.scalars)
    implementation(libs.retrofit2.converter.simplexml)
    implementation(libs.gson)
    implementation(libs.logging.interceptor)

    // Country Code Picker
    implementation(libs.ccp)

    //otp
    implementation(libs.otpview)

    //dots indicator
    implementation(libs.dotsindicator)

    //google login
//    implementation(libs.gms.play.services.auth)


    implementation(libs.androidx.viewpager2)
    implementation(libs.tbuonomo.dotsindicator)

    // Glide
    implementation(libs.glide)

    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    implementation(libs.threetenabp)



}