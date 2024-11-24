package com.atg.currencyconverter

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val deps = target.extensions.getByType<VersionCatalogsExtension>().named("libs")
        target.pluginManager.apply {
            apply(deps.findPlugin("android.application").get().get().pluginId)
            apply(deps.findPlugin("kotlin.android").get().get().pluginId)
        }
        target.extensions.configure<BaseAppModuleExtension>(::configureAndroid)
        target.dependencies {}
    }

    private fun configureAndroid(commonExtension: BaseAppModuleExtension) {
        commonExtension.apply {
            compileSdk = 35
            defaultConfig { minSdk = 26 }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }
            buildFeatures.buildConfig = true
        }
    }
}