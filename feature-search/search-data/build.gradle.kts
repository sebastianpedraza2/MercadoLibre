plugins {
    id("mercadolibre.android.feature")
}

android {
    namespace = "com.pedraza.sebastian.search_data"
}

dependencies {

    //Modules
    implementation(project(":feature-search:search-domain"))

    implementation(libs.retrofit.core)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)
    implementation(libs.gson.converter)
}