plugins {
    alias(libs.plugins.atg.android.library)
}


dependencies {
    implementation(project(":core:common"))
    implementation(libs.coroutines.core)
    implementation(libs.fragment.ktx)
}