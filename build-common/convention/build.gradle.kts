plugins {
    `kotlin-dsl`
}

group = "com.atg.currencyconverter.buildcommon"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    compileOnly(libs.android.tools.build.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    /**
     * Register convention plugins so they are available in the build scripts of the application
     */
    plugins {

        register("atgAndroidApp") {
            id = "atg.android.app"
            implementationClass = "com.atg.currencyconverter.AndroidApplicationConventionPlugin"
        }

        register("atgAndroidLibrary") {
            id = "atg.android.library"
            implementationClass = "com.atg.currencyconverter.AndroidLibraryConventionPlugin"
        }

        register("atgKotlinLibrary") {
            id = "atg.kotlin.library"
            implementationClass = "com.atg.currencyconverter.KotlinLibraryConventionPlugin"
        }
    }
}
