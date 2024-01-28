plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    //id("maven-publish")
    id("maven-publish")
}

android {
    namespace = "com.easit.id_image"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    publishing {
        singleVariant("release") {
            // if you don't want sources/javadoc, remove these lines
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation("com.google.mlkit:text-recognition:16.0.0")
    implementation("androidx.compose.ui:ui-graphics:1.5.1")
    //implementation("com.github.User:Repo:Tag")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.Olaoluwa99"
            artifactId = "id-image"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

//afterEvaluate {
//    publishing {
//        publications {
//            release(MavenPublication) {
//                from components.release
//
//                groupId = 'com.github.Olaoluwa99'
//                artifactId = 'id-image'
//                version = '1.0'
//            }
//        }
//    }
//}