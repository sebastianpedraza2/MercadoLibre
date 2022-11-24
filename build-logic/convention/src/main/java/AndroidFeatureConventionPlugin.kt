import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("mercadolibre.android.library")
                apply("mercadolibre.android.hilt")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", project(":core"))

                add("testImplementation", libs.findLibrary("junit4").get())
                add("testImplementation", libs.findLibrary("androidx-test-ext").get())
                add("testImplementation", libs.findLibrary("androidx.test.core").get())
                add("testImplementation", libs.findLibrary("kotlinx.coroutines.test").get())
                add("testImplementation", libs.findLibrary("turbine").get())
                add("testImplementation", libs.findLibrary("androidx.compose.ui.test").get())
                add("testImplementation", libs.findLibrary("hilt.android.testing").get())
                add("testImplementation", libs.findLibrary("androidx.test.runner").get())
                add("testImplementation", libs.findLibrary("truth").get())
                add("testImplementation", libs.findLibrary("mockk").get())
                add("testImplementation", libs.findLibrary("mockwebserver").get())

                add("androidTestImplementation", libs.findLibrary("junit4").get())
                add("androidTestImplementation", libs.findLibrary("androidx-test-ext").get())
                add("androidTestImplementation", libs.findLibrary("kotlinx.coroutines.test").get())
                add("androidTestImplementation", libs.findLibrary("turbine").get())
                add("androidTestImplementation", libs.findLibrary("androidx.compose.ui.test").get())
                add("androidTestImplementation", libs.findLibrary("truth").get())
                add("androidTestImplementation", libs.findLibrary("mockk").get())
            }
        }
    }
}