plugins {
    id("mercadolibre.android.feature")

}

android {
    namespace = "com.pedraza.sebastian.search_domain"
}

dependencies {

    // Modules
    implementation(project(":android-helpers"))

    implementation(libs.gson)
}
