plugins {
    id("mercadolibre.android.application")
    id("mercadolibre.android.application.compose")
    id("mercadolibre.android.hilt")
}

android {
    namespace = "com.pedraza.sebastian.mercadolibre"
    defaultConfig {
        applicationId = "com.pedraza.sebastian.mercadolibre"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = ".debug"
        }
        val release by getting {
            applicationIdSuffix = ".release"
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    //Modules
    implementation(project(":core"))
    implementation(project(":feature-search:search-presentation"))
    implementation(project(":feature-search:search-domain"))
    implementation(project(":feature-search:search-data"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.testManifest)
    implementation(libs.retrofit.core)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)
    implementation(libs.gson.converter)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.android.material)
    androidTestImplementation(libs.androidx.test.espresso.core)
}