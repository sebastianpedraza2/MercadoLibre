plugins {
    id("mercadolibre.android.library")
    id("mercadolibre.android.hilt")
    id("mercadolibre.android.library.compose")
}

android {
    namespace = "com.pedraza.sebastian.core"
}

dependencies{
    implementation(libs.androidx.core.ktx)
    implementation(libs.retrofit.core)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.runtime)
    debugApi(libs.androidx.compose.ui.tooling)
}
