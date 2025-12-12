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
    val dayRegex = Regex("""day(\d{2})""")
    val yearRegex = Regex("""aoc(\d{4})""")

    file("src/main/kotlin")
        .walkTopDown()
        .filter { it.isFile && it.extension == "kt" }
        // Early filtering: only process files in aoc{year}/day{dd}/ structure
        .filter { file ->
            dayRegex.matches(file.parentFile.name) &&
            yearRegex.matches(file.parentFile.parentFile.name)
        }
        // Only read file content for files matching the directory pattern
        .filter { file ->
            file.useLines { lines ->
                lines.any { line -> line.contains("fun main(") }
            }
        }
        .forEach { file ->
            val day = dayRegex.find(file.parentFile.name)!!.value
            val year = file.parentFile.parentFile.name.removePrefix("aoc")
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
