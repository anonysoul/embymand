package com.anonysoul.embymand.build.plugin

import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.embeddedKotlinVersion
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.utils.IMPLEMENTATION
import com.anonysoul.embymand.build.dependencie.Modules
import com.anonysoul.embymand.build.dependencie.Versions
import org.springframework.boot.gradle.plugin.SpringBootPlugin

class BasePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            group = "com.anonysoul.embymand"
            version = Versions.PROJECT

            apply(plugin = "org.jetbrains.kotlin.jvm")
            apply(plugin = "kotlin-spring")
            apply(plugin = "io.spring.dependency-management")
            apply(plugin = "org.jlleitschuh.gradle.ktlint")

            repositories {
                add(mavenLocal())
                add(mavenCentral())
            }

            configure<DependencyManagementExtension> {
                imports {
                    mavenBom(SpringBootPlugin.BOM_COORDINATES) {
                        bomProperty("kotlin.version", embeddedKotlinVersion)
                    }
                }
            }

            tasks.withType<KotlinCompile> {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_21)
                    // 启用 jvm-default, 这是因为在 server 项目中我们使用了 mapstruct, mapstruct 使用接口的默认方法来提供转换函数的默认实现,
                    // 而在 kotlin 中要生成 jvm 的接口默认方法, 需要启用 jvm-default 编译选项
                    freeCompilerArgs.add("-Xjvm-default=all")
                }
            }

            dependencies {
                add(IMPLEMENTATION, Modules.slf4jApi)
            }

            tasks.withType<Test> {
                useJUnitPlatform()
            }
        }
    }
}
