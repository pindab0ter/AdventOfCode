package nl.pindab0ter.aoc2018.day07

import nl.pindab0ter.aoc.getInput

fun main() = getInput(2018, 7)
    .lines()
    .map { it[5] to it[36] }
    .let { input ->
        print(
            """
            --- Day 7: Sum of Its Parts ---

            Part one: In what order should the steps in your instructions be completed?
            ${SumOfItsParts(input).correctOrder()}

            Part two: With 5 workers and the 60+ second step durations described above, how long will it take to complete all of the steps?
            ${SumOfItsParts(input).teamwork(5, 60)}

            """.trimIndent()
        )
    }

class SumOfItsParts(input: List<Pair<Char, Char>>) {
    private val characters = input.flatMap(Pair<Char, Char>::toList).toSortedSet()
    private val prerequisites = input                // Step X must be finished before step Y can begin.
        .groupBy { (_, step) -> step }               // Group prerequisites by step
        .mapValues { (_, prerequisite) ->
            prerequisite.map { it.first }.distinct() // Remove the step from the list of it's prerequisites
        }


    // Credit to this solution to u/nutrecht
    // https://github.com/nielsutrecht/adventofcode/blob/master/src/main/kotlin/com/nibado/projects/advent/y2018/Day07.kt
    // Comments by me
    fun correctOrder(): String {
        val result = mutableListOf<Char>()
        while (result.size < characters.size) {
            result += characters
                .filterNot { result.contains(it) }       // Exclude taken steps
                .first { step ->
                    // Step has no prerequisites     OR all prerequisites have been met
                    !prerequisites.containsKey(step) || prerequisites[step]!!.all { result.contains(it) }
                }
        }

        return result.joinToString("")
    }

    fun teamwork(workers: Int, timePerStep: Int): Int {
        val completedSteps = mutableListOf<Char>()
        var idleWorkers = workers
        var timeTaken = 0
        val inProgress = mutableMapOf<Char, Int>()

        while (completedSteps.size < characters.size) {
            // Determine which steps have been finished by now
            inProgress.filter { (_, doneBy) -> doneBy == timeTaken }.keys.sorted()
                // Move each completed step from in progress to completed and free up a worker
                .forEach { step ->
                    inProgress.remove(step)
                    completedSteps.add(step)
                    idleWorkers++
                }

            characters
                //                 Step has not yet been completed OR started work on
                .filterNot { step -> completedSteps.contains(step) || inProgress.keys.contains(step) }
                //                       Step has no prerequisites OR all prerequisites have been met
                .filter { step ->
                    !prerequisites.containsKey(step) || prerequisites[step]!!.all {
                        completedSteps.contains(
                            it
                        )
                    }
                }
                // Each free worker takes on a step in alphabetical order
                .sorted().take(idleWorkers).also { idleWorkers -= it.size }
                // Determine by when each step will be done
                .forEach { step -> inProgress[step] = timeTaken + (step.time() + timePerStep + 1) }

            timeTaken++
        }
        return timeTaken - 1
    }
}

fun Char.time(): Int = this - 'A'
