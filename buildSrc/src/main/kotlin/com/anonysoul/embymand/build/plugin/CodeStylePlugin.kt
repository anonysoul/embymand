package com.anonysoul.embymand.build.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class CodeStylePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            apply(plugin = "org.jlleitschuh.gradle.ktlint")
        }
    }
}