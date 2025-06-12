package com.anonysoul.embymand.build.dependencie

object Modules {
    //
    // spring-boot-starter-xxx 这些是用于 spring boot 自动装配的, 只应当在带有 main 函数的模块中使用
    //

    val springBootStarter = "org.springframework.boot:spring-boot-starter"
    val springBootStarterDataJpa = "org.springframework.boot:spring-boot-starter-data-jpa"
    val springBootStarterJdbc = "org.springframework.boot:spring-boot-starter-jdbc"
    val springBootStarterValidation = "org.springframework.boot:spring-boot-starter-validation"

    //
    // 接下来是各个单独的 spring 模块,
    //
    // 当我们的非启动模块(带有main函数的模块)要使用 spring 的相关功能时, 直接引用下面的独立的 spring 模块,
    // 不要使用 spring-boot-starter-xxx 模块, spring-boot-starter-xxx 模块中乱七八糟的东西太多了
    //

    val springTx = "org.springframework:spring-tx"
    val springBeans = "org.springframework:spring-beans"
    val springContext = "org.springframework:spring-context"
    val springContextSupport = "org.springframework:spring-context-support"
    val springDataJpa = "org.springframework.data:spring-data-jpa"
    val springBootAutoConfigure = "org.springframework.boot:spring-boot-autoconfigure"


    //
    // 其他依赖的模块
    //
    // 这些模块是用于非 spring 模块的, 例如 hibernate, jackson, guava 等等,
    // 如果该模块在 spring boot dependencies 中写明了版本, 那么我们就不需要手动指定版本, 除非我们要覆盖这个版本.
    //
    // spring boot 自动管理的版本见 `https://docs.spring.io/spring-boot/appendix/dependency-versions/coordinates.html`
    //

    val assertjCore = "org.assertj:assertj-core"
    val hikariCP = "com.zaxxer:HikariCP"
    val hibernateCore = "org.hibernate.orm:hibernate-core"
    val slf4jApi = "org.slf4j:slf4j-api"
    val aspectJRT = "org.aspectj:aspectjrt"
    val jakartaServelet = "jakarta.servlet:jakarta.servlet-api"
    val jakartaMail = "org.eclipse.angus:jakarta.mail"
    val jakartaMailApi = "jakarta.mail:jakarta.mail-api"
    val jakartaPersistence = "jakarta.persistence:jakarta.persistence-api"
    val jakartaTransaction = "jakarta.transaction:jakarta.transaction-api"
    val jacksonDatabind = "com.fasterxml.jackson.core:jackson-databind"
    val jacksonModuleKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin"
    val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core"
    val kotlinCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test"
    val postgresql = "org.postgresql:postgresql"
    val jakartaValidation = "jakarta.validation:jakarta.validation-api"
    val jakartaAnnotion = "jakarta.annotation:jakarta.annotation-api"
    val guava = "com.google.guava:guava:${Versions.guava}"
    val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlin}"
    val testContainersCore = "org.testcontainers:testcontainers"
    val testContainersPostgresql = "org.testcontainers:postgresql"
    val testContainersJunit = "org.testcontainers:junit-jupiter"
    val batikRasterizer = "org.apache.xmlgraphics:batik-rasterizer:${Versions.batikRasterizer}"
    val reactivestreams = "org.reactivestreams:reactive-streams"
    val httpclient5 = "org.apache.httpcomponents.client5:httpclient5"
    val flywayCore = "org.flywaydb:flyway-core"
    val flywayDatabasePostgres = "org.flywaydb:flyway-database-postgresql"
    val persistenceApi = "jakarta.persistence:jakarta.persistence-api:${Versions.persistenceApi}"
    val telegramBotApi = "com.github.pengrad:java-telegram-bot-api:${Versions.telegramBotApi}"
    val unbescape = "org.unbescape:unbescape:${Versions.unbescape}"
    val embyclient = "media.emby:embyclient:${Versions.embyclient}"
}