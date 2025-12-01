import org.gradle.internal.extensions.stdlib.capitalized
import java.net.URI

plugins {
    kotlin("jvm") version "2.1.0"
    id("dev.clojurephant.clojure") version "0.8.0-beta.7"
    idea
    application
}

group = "nl.pindab0ter"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        name = "clojars"
        url = URI("https://repo.clojars.org")
    }
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.9.0")
    implementation("io.arrow-kt", "arrow-core", "2.0.0-rc.1")
    implementation("com.github.kittinunf.fuel", "fuel", "3.0.0-alpha04")

    implementation("com.github.ajalt.mordant", "mordant", "3.0.1")
    implementation("com.github.ajalt.mordant", "mordant-coroutines", "3.0.1")

    testImplementation("org.junit.jupiter", "junit-jupiter", "5.11.3")
    testImplementation("org.junit.jupiter", "junit-jupiter-params", "5.11.3")

    testImplementation(kotlin("test"))

    // Clojure
    implementation("org.clojure", "clojure", "1.12.0")
    implementation("org.clojure", "tools.namespace", "1.5.0")
    implementation("clj-http", "clj-http", "3.12.4")

    testRuntimeOnly("dev.clojurephant", "jovial", "0.4.2")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("nl.pindab0ter.ScratchKt")
}

// Dynamically create run tasks for each Kotlin file with a main function in it
file("src/main/kotlin")
    .walkTopDown()
    .filter<File> { it.isFile && it.extension == "kt" && it.readText().contains("fun main(") }
    .toList<File>().forEach { file ->
        val matchResult = Regex("""day\d{2}""").find(file.parentFile.name) ?: return@forEach

        val day = matchResult.value
        val year = file.parentFile.parentFile.name.removePrefix("aoc")
        val taskName = "runKotlin${year}${day}"
        tasks.register<JavaExec>(taskName) {
            group = "application"
            description = "Run the ${file.nameWithoutExtension} file"
            val packageName = file.parentFile.path.replace("/", ".").substringAfter("src.main.kotlin.")
            val className = file.nameWithoutExtension.capitalized() + "Kt"
            mainClass.set("$packageName.$className")
            classpath = sourceSets["main"].runtimeClasspath
        }
    }
