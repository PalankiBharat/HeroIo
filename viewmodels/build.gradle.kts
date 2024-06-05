plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
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
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
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
                implementation(libs.koin.annotations)
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
}

android {
    namespace = "com.hero.viewmodels"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
