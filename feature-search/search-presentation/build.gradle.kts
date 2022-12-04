plugins {
    id("mercadolibre.android.feature")
    id("mercadolibre.android.library.compose")
}

android {
    namespace = "com.pedraza.sebastian.search_presentation"
}

dependencies {

    //Modules
    implementation(project(":feature-search:search-domain"))
    implementation(project(":android-helpers"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.testManifest)
    implementation(libs.androidx.lifecycle.viewModelCompose)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.coil.kt.compose)
}