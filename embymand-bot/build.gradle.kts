import com.anonysoul.embymand.build.Utils
import com.anonysoul.embymand.build.dependencie.Modules
import com.anonysoul.embymand.build.dependencie.Versions
import com.anonysoul.embymand.build.plugin.DockerBuildImage
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("embymand-base")
    id("embymand-codestyle")
    id("org.springframework.boot")
}

dependencies {
    implementation(Modules.springBootStarter)
    implementation(Modules.springBootStarterDataJpa)
    implementation(Modules.springBootStarterValidation)
    implementation(Modules.unbescape)

    implementation(project(":embymand-infra:telegram-bot"))
    implementation(project(":embymand-service:embymand-service-emby"))
    implementation(project(":embymand-service:embymand-service-integral"))
    implementation(project(":embymand-service:embymand-service-user"))

    runtimeOnly(Modules.postgresql)
}

tasks {
    bootJar {
        manifest {
            attributes("Main-Class" to "org.springframework.boot.loader.launch.PropertiesLauncher")
        }
    }

    val buildDockerImage =
        create("buildDockerImage", DockerBuildImage::class) {
            group = "build"

            val bootJarWithoutVersion =
                (get("bootJar") as BootJar).apply {
                    archiveVersion.set("")
                }

            val copyBootJarToDockerDir =
                create("copyBootJarToDockerDir", Copy::class) {
                    dependsOn(bootJarWithoutVersion)

                    from(bootJarWithoutVersion.archiveFile.get())
                    into("docker/app/")
                }

            dependsOn(copyBootJarToDockerDir)

            inputDir.set(file("docker"))
            outputFile.set(projectDir.toPath().resolve("build/docker-image-id.txt").toFile())
            buildArgs.put("VERSION", Versions.PROJECT)
            buildArgs.put("JAR_NAME", bootJarWithoutVersion.archiveFileName.get())
            tags.add(Utils.dockerImageName("embymand-bot"))
        }

    build {
        finalizedBy(buildDockerImage)
    }
}
