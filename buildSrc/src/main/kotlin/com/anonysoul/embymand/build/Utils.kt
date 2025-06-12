package com.anonysoul.embymand.build

import com.anonysoul.embymand.build.dependencie.Versions.PROJECT

object Utils {

    private const val DOCKER_IMAGE_PREFIX = "anonysoul"

    fun dockerImageName(name: String) = "$DOCKER_IMAGE_PREFIX/$name:$PROJECT"
}