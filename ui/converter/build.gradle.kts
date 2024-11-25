plugins {
    alias(libs.plugins.atg.android.library)
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:base"))
    implementation(project(":data:market"))
    implementation(project(":data:account"))
    implementation(project(":domain:converter"))
    implementation(project(":domain:account"))
    implementation(libs.coroutines.core)
    implementation(libs.bundles.di.koin)
    implementation(libs.material)
    implementation(libs.appcompat)
    implementation(libs.fragment.ktx)
    implementation(libs.material.dialog)
}