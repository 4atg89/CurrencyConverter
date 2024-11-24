package com.atg.currencyconverter

import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val deps = target.extensions.getByType<VersionCatalogsExtension>().named("libs")
        with(target) {
            target.pluginManager.apply {
                apply(deps.findPlugin("android.library").get().get().pluginId)
                apply(deps.findPlugin("kotlin.android").get().get().pluginId)
            }

            androidLibrary {
                compileSdk = 35
                defaultConfig { minSdk = 26 }

                namespace = "com.atg.${target.name}"

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                }

                buildFeatures {
                    viewBinding = true
                    dataBinding = true
                }
            }
        }
    }


    /**
     * Configures the [android][com.android.build.gradle.LibraryExtension] extension.
     */
    private fun Project.`androidLibrary`(configure: Action<com.android.build.gradle.LibraryExtension>): Unit =
        (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("android", configure)

}