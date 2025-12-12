import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
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
    jvmToolchain(23)
}
