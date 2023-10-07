import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


group = "me.user"
version = "1.0-SNAPSHOT"

val kotestVersion: String by project

plugins {
    kotlin("jvm") version "1.8.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:${kotestVersion}")
    testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}

application {
    mainClass.set("chapter_one.FibonacciKt")
}

