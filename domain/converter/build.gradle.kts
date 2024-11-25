plugins {
    alias(libs.plugins.atg.kotlin.library)
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.coroutines.core)
}