plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "viewmodels"
            isStatic = true
        }
    }

  /*  sourceSets.all {
        languageSettings.enableLanguageFeature("ExplicitBackingFields")
    }*/
    sourceSets {
        commonMain{
            dependencies {
                //put your multiplatform dependencies here
                implementation(project(":domain"))
                implementation(project(":data"))
                implementation(libs.androidx.lifecycle.viewmodel)
                //Kotlin Coroutines
                implementation(libs.kotlinx.coroutines.core)
                //Koin
                implementation(libs.koin.core)
                implementation(libs.koin.composeVM)
            }
        }
    }
}

android {
    namespace = "com.hero.viewmodels"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
