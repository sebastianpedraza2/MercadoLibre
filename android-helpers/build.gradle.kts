plugins {
    id("mercadolibre.android.library")
    id("mercadolibre.android.hilt")
    id("mercadolibre.android.library.compose")
}

android {
    namespace = "com.pedraza.sebastian.android_helpers"
}

dependencies {
    //Modules
    implementation(project(":core"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.testManifest)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.coil.kt.compose)
}