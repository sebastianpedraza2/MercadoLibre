plugins {
    id("mercadolibre.android.feature")
}

android {
    namespace = "com.pedraza.sebastian.feature_search"
}

dependencies {

    //Modules
    implementation(project(":feature-search:search-presentation"))
    implementation(project(":feature-search:search-domain"))
    implementation(project(":feature-search:search-data"))
}