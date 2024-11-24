plugins {
    alias(libs.plugins.atg.kotlin.library)
    id("kotlinx-serialization")
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.di.koin.core)
    implementation(libs.bundles.network)
    implementation(libs.kotlinx.serialization)
}