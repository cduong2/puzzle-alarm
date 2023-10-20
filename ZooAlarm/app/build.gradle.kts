plugins {
    id("com.android.application")
//    id("kotlin-kapt")
}

android {
    namespace = "com.example.zooalarm"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.zooalarm"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    //Room
//    val room_version = "2.5.0"
//    implementation("androidx.room:room-ktx:$room_version")
//    annotationProcessor("androidx.room:room-compiler:$room_version")

    implementation("androidx.room:room-ktx:2.5.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")

    //stuff from outdated tutorial
//    // Lifecycle https://developer.android.com/jetpack/androidx/releases/lifecycle
//    val lifecycle_version = "2.6.2"
//    val arch_version = "2.2.0"
//    // ViewModel
//    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
//    // LiveData
//    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
//    // Annotation processor
//    annotationProcessor("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")
//        // alternately - if using Java8, use the following instead of lifecycle-compiler
//        //    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")
//
//    //Room
//    val room_version = "2.6.0"
//
//    implementation("androidx.room:room-runtime:$room_version")
//    annotationProcessor("androidx.room:room-compiler:$room_version")


    // default dependencies
    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("androidx.appcompat:design:1.6.1") //appcompat
//    implementation("androidx.appcompat:cardview:1.6.1") //appcompat
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.4.0-alpha01")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }
}