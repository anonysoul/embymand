package com.anonysoul.embymand.build.dependencie

object Versions {
    val PROJECT = System.getenv("VERSION") ?: "dev"

    val guava = "31.1-jre"
    val mockitoKotlin = "5.4.0"
    val batikRasterizer = "1.16"
    val persistenceApi = "3.1.0"
    val telegramBotApi = "8.3.0"
    val unbescape = "1.1.6.RELEASE"
    val embyclient = "4.9.0.32"
}