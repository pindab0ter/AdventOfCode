import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.internal.os.OperatingSystem
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import java.net.URI

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.clojurephant)
    alias(libs.plugins.versions)
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
    // Kotlin Standard Libraries
    implementation(kotlin("stdlib"))

    // Coroutines and Functional Programming
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.arrow.core)

    // HTTP Client
    implementation(libs.fuel)

    // Terminal UI
    implementation(libs.mordant)
    implementation(libs.mordant.coroutines)

    // Logging
    implementation(libs.kotlin.logging)
    runtimeOnly(libs.slf4j.simple)

    // Testing
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(kotlin("test"))
    testRuntimeOnly(libs.junit.platform.launcher)

    // Clojure
    implementation(libs.clojure)
    implementation(libs.clojure.tools.namespace)
    implementation(libs.clj.http)
    testRuntimeOnly(libs.jovial)
}

// Test configuration
tasks.test {
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed")
    }
}

application {
    mainClass.set("nl.pindab0ter.ScratchKt")
}

// Dynamically create run tasks for each Advent of Code solution
generateAdventOfCodeTasks()

tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
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

kotlin {
    jvmToolchain(17)
}

// OPENRNDR configuration
class Openrndr {
    val openrndrVersion = "0.4.5"
    val orxVersion = "0.4.5"

    val currentArchitecture: String = DefaultNativePlatform("current").architecture.name
    val currentOs: OperatingSystem = OperatingSystem.current()
    val os = when {
        currentOs.isWindows -> "windows"
        currentOs.isMacOsX -> when (currentArchitecture) {
            "aarch64", "arm-v8" -> "macos-arm64"
            else -> "macos"
        }
        currentOs.isLinux -> when (currentArchitecture) {
            "x86-64" -> "linux-x64"
            "aarch64" -> "linux-arm64"
            else -> throw IllegalArgumentException("architecture not supported: $currentArchitecture")
        }
        else -> throw IllegalArgumentException("os not supported: ${currentOs.name}")
    }

    fun orx(module: String) = "org.openrndr.extra:$module:$orxVersion"
    fun openrndr(module: String) = "org.openrndr:openrndr-$module:$openrndrVersion"
    fun openrndrNatives(module: String) = "org.openrndr:openrndr-$module-natives-$os:$openrndrVersion"

    init {
        dependencies {
            runtimeOnly(openrndr("gl3"))
            runtimeOnly(openrndrNatives("gl3"))
            runtimeOnly(openrndrNatives("ffmpeg"))
            implementation(openrndr("application"))
            implementation(openrndr("ffmpeg"))
        }
    }
}

val openrndr = Openrndr()
