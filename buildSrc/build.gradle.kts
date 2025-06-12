plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$embeddedKotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-noarg:${embeddedKotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-allopen:${embeddedKotlinVersion}")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.4.3")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.7")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.3.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:7.13.0")
    implementation("org.flywaydb:flyway-core:10.20.1")
    implementation("org.flywaydb:flyway-database-postgresql:10.20.1")
    implementation("com.github.docker-java:docker-java-core:3.5.1")
    implementation(platform("org.testcontainers:testcontainers-bom:1.20.5"))
    implementation("org.testcontainers:postgresql")
    implementation("org.postgresql:postgresql:42.7.5")
}

gradlePlugin {
    plugins {
        register("embymand-base-plugin") {
            id = "embymand-base"
            implementationClass = "com.anonysoul.embymand.build.plugin.BasePlugin"
        }
        register("embymand-codestyle-plugin") {
            id = "embymand-codestyle"
            implementationClass = "com.anonysoul.embymand.build.plugin.CodeStylePlugin"
        }
        register("embymand-jpa-plugin") {
            id = "embymand-jpa"
            implementationClass = "com.anonysoul.embymand.build.plugin.JpaPlugin"
        }
    }
}
