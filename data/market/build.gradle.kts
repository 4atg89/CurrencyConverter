plugins {
    alias(libs.plugins.atg.kotlin.library)
}

dependencies {
    implementation(project(":domain:converter"))
    implementation(project(":data:network"))
    implementation(libs.di.koin.core)
    implementation(libs.coroutines.core)
}