plugins {
    alias(libs.plugins.atg.kotlin.library)
}

dependencies {
    implementation(project(":domain:account"))
    implementation(project(":data:storage:api"))
    implementation(libs.di.koin.core)
}