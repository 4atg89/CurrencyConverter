package com.atg.currencyconverter

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class KotlinLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val deps = target.extensions.getByType<VersionCatalogsExtension>().named("libs")
        target.pluginManager.apply(deps.findPlugin("kotlin.jvm").get().get().pluginId)
    }

}