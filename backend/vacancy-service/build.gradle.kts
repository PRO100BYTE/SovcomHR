plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.0"
    kotlin("plugin.jpa") version "1.8.0"
    kotlin("plugin.spring") version "1.8.0"
    id("org.springframework.boot") version "2.7.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.openapi.generator") version "6.3.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("org.postgresql:postgresql:42.5.3")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.8")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
}

tasks.named("compileKotlin") {
    dependsOn("generateAllSpecs")
}

val apiList = listOf("VacancyApi")

val generateTasks = apiList.map {
    tasks.register(it + "_generate", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class.java) {
        generatorName.set("spring")
        inputSpec.set("$rootDir/../APIs/$it.yaml")
        outputDir.set("$buildDir/generated")
        modelPackage.set("com.pro100byte.dto")
        apiPackage.set("com.pro100byte.api")
        generateModelTests.set(false)
        generateApiTests.set(false)
        configOptions.set(
            mutableMapOf(
                "interfaceOnly" to "true",
                "useTags" to "true",
                "useSwaggerUI" to "false"
            )
        )
    }
}

sourceSets["main"].java {
    srcDirs("$buildDir/generated/src/main/java")
}

tasks.register("generateAllSpecs") {
    group = "api"

    dependsOn(generateTasks)
}

application {
    mainClass.set("com.pro100byte.AppKt")
}
