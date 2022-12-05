plugins {
    id("mercadolibre.android.feature")
}

tasks.register("runUnitTests") {
    dependsOn(":android-helpers:test", ":app:test", ":core:test", ":feature-search:search-presentation:test",
        ":feature-search:search-domain:test", ":feature-search:search-data:test", ":feature-search:test",
    )
    group = "CI"
    description = "$ ./gradlew runUnitTests # to run on a CI pipeline"
}

android {
    namespace = "com.pedraza.sebastian.feature_search"
}

dependencies {

    //Modules
    implementation(project(":feature-search:search-presentation"))
    implementation(project(":feature-search:search-domain"))
    implementation(project(":feature-search:search-data"))
    implementation(project(":android-helpers"))

    implementation(libs.gson)
    implementation(libs.retrofit.core)
}