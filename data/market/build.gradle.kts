plugins {
    alias(libs.plugins.atg.kotlin.library)
}

dependencies {
    implementation(project(":domain:converter"))
}