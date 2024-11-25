plugins {
    alias(libs.plugins.atg.android.library)
}

dependencies {
    api(project(":data:storage:api"))
    implementation(libs.di.koin.core)
    implementation(libs.coroutines.core)
}