import org.gradle.internal.extensions.stdlib.capitalized
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    kotlin("jvm") version "2.2.21"
    id("dev.clojurephant.clojure") version "0.9.1"
    id("com.github.ben-manes.versions") version "0.53.0"
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
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.10.2")
    implementation("io.arrow-kt", "arrow-core", "2.2.0")
    implementation("com.github.kittinunf.fuel", "fuel", "3.0.0-alpha04")

    implementation("com.github.ajalt.mordant", "mordant", "3.0.2")
    implementation("com.github.ajalt.mordant", "mordant-coroutines", "3.0.2")

    testImplementation(platform("org.junit:junit-bom:6.0.1"))
    testImplementation("org.junit.jupiter", "junit-jupiter")
    testImplementation("org.junit.jupiter", "junit-jupiter-params")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation(kotlin("test"))

    // Clojure
    implementation("org.clojure", "clojure", "1.12.4")
    implementation("org.clojure", "tools.namespace", "1.5.0")
    implementation("clj-http", "clj-http", "3.13.1")

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

tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates") {
    fun isUnstable(version: String): Boolean {
        val containsStableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.contains(it, ignoreCase = true) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        return !containsStableKeyword && !regex.matches(version)
    }

    rejectVersionIf {
        val candidateIsUnstable = isUnstable(candidate.version)
        val currentVersionIsStable = !isUnstable(currentVersion)

        candidateIsUnstable && currentVersionIsStable
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(17)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget = JVM_17
}
