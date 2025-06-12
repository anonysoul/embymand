package com.anonysoul.embymand.build.plugin

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.BuildImageResultCallback
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.time.Duration

const val DOCKER_SOCKET_PATH = "/var/run/docker.sock"
val SOCKET_LOCATION = System.getenv("DOCKER_HOST") ?: "unix://$DOCKER_SOCKET_PATH"

fun withDockerClient(block: (dockerClient: DockerClient) -> Unit) {
    val config = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost(SOCKET_LOCATION)
        .withDockerTlsVerify(false).build()
    val httpClient =
        ZerodepDockerHttpClient.Builder().dockerHost(config.getDockerHost()).connectionTimeout(Duration.ofSeconds(30))
            .responseTimeout(Duration.ofSeconds(60)).build()
    val dockerClient = DockerClientImpl.getInstance(config, httpClient);
    try {
        block(dockerClient)
    } finally {
        httpClient.close()
    }
}

abstract class DockerBuildImage : DefaultTask() {

    @get:Input
    abstract val buildArgs: MapProperty<String, String>

    @get:InputDirectory
    abstract val inputDir: Property<File>

    @get:Input
    abstract val tags: SetProperty<String>

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    @TaskAction
    fun run() {
        withDockerClient { dockerClient ->
            val cmd = dockerClient.buildImageCmd()
                .withBaseDirectory(inputDir.get())
                .withDockerfile(File(inputDir.get(), "Dockerfile"))
                .withTags(tags.getOrElse(setOf()))
            buildArgs.orNull?.forEach { k, v -> cmd.withBuildArg(k, v) }
            val result = cmd.exec(BuildImageResultCallback())

            result.awaitCompletion()

            if (outputFile.isPresent) {
                val writer =
                    BufferedWriter(OutputStreamWriter((FileOutputStream(outputFile.get().asFile)), Charsets.UTF_8))
                try {
                    writer.write(result.awaitImageId())
                    writer.newLine()
                } finally {
                    writer.close()
                }
            }

            println("成功构建镜像, imageId=${result.awaitImageId()}")
        }
    }
}
