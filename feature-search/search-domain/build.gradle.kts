plugins {
    id("mercadolibre.android.feature")

}

android {
    namespace = "com.pedraza.sebastian.search_domain"
}

dependencies {

    //Modules
    implementation(project(":feature-search:search-data"))
}