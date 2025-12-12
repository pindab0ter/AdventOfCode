import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.register

/**
 * Generates run tasks for all Kotlin files containing a main function.
 * Tasks are named runKotlin{YEAR}{DAY}, e.g. runKotlin2025day01
 */
fun Project.generateAdventOfCodeTasks() {
    val sourceSets = extensions.getByName("sourceSets") as SourceSetContainer

    file("src/main/kotlin")
        .walkTopDown()
        .filter { it.isFile && it.extension == "kt" && it.readText().contains("fun main(") }
        .mapNotNull { file ->
            val dayMatch = Regex("""day(\d{2})""").find(file.parentFile.name)
            val year = file.parentFile.parentFile.name.removePrefix("aoc")

            dayMatch?.let { Triple(file, year, it.value) }
        }
        .forEach { (file, year, day) ->
            val taskName = "runKotlin$year$day"

            tasks.register<JavaExec>(taskName) {
                group = "application"
                description = "Run ${file.nameWithoutExtension}"

                val packageName = file.parentFile.path
                    .substringAfter("src/main/kotlin/")
                    .replace("/", ".")

                mainClass.set("$packageName.${file.nameWithoutExtension}Kt")
                classpath = sourceSets.getByName("main").runtimeClasspath
            }
        }
}
