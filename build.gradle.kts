// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.atg.android.application) apply false
    alias(libs.plugins.atg.android.library) apply false
    alias(libs.plugins.atg.kotlin.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
}