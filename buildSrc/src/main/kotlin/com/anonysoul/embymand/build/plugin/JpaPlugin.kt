package com.anonysoul.embymand.build.plugin

import com.anonysoul.embymand.build.dependencie.Modules
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.utils.IMPLEMENTATION

class JpaPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(plugins) {
                apply("org.jetbrains.kotlin.plugin.jpa")
            }

            dependencies {
                add(IMPLEMENTATION, Modules.hibernateCore)
                add(IMPLEMENTATION, Modules.springDataJpa)
                add(IMPLEMENTATION, Modules.jakartaPersistence)
                add(IMPLEMENTATION, "org.jetbrains.kotlin:kotlin-reflect:2.1.21")
            }
        }
    }
}