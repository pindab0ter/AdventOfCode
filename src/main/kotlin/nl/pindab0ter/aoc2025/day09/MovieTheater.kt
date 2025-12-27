package nl.pindab0ter.aoc2025.day09

import kotlinx.coroutines.Dispatchers
import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.collections.combinations
import nl.pindab0ter.lib.openrndr.FPSDisplay
import nl.pindab0ter.lib.openrndr.ScaleTransform
import nl.pindab0ter.lib.openrndr.fromPoint
import nl.pindab0ter.lib.println
import nl.pindab0ter.lib.types.OrthogonalPolygon
import nl.pindab0ter.lib.types.Point
import nl.pindab0ter.lib.types.Rectangle
import org.openrndr.KEY_ESCAPE
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.color.ColorRGBa.Companion.GRAY
import org.openrndr.color.ColorRGBa.Companion.GREEN
import org.openrndr.color.ColorRGBa.Companion.MAGENTA
import org.openrndr.color.ColorRGBa.Companion.RED
import org.openrndr.color.ColorRGBa.Companion.TRANSPARENT
import org.openrndr.color.ColorRGBa.Companion.WHITE
import org.openrndr.draw.isolated
import org.openrndr.ffmpeg.ScreenRecorder
import org.openrndr.launch
import org.openrndr.math.Vector2
import org.openrndr.shape.ShapeContour
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

/**
 * See video in `art` directory.
 */
fun main() {
    val points = getInput(2025, 9).parse()

    val largestPossibleSurface = points.findLargestRectangle().surface
    println("The largest area of any rectangle is: $largestPossibleSurface")

    val largestValidRectangle = points.renderFindingLargestValidRectangle(insertDelay = true)
    println("The largest area of any valid rectangle is: ${largestValidRectangle.surface}")
}

fun List<Point>.findLargestValidRectangle(
    onRectangleChecked: ((rectangle: Rectangle, isValid: Boolean) -> Unit)? = null,
): Rectangle {
    val polygon = OrthogonalPolygon(this)
    val allPossibleRectangles = this.combinations().map(::Rectangle).iterator()
    var largestValidRectangle: Rectangle? = null

    while (allPossibleRectangles.hasNext()) {
        val rectangle = allPossibleRectangles.next()
        val isValid = !(polygon intersectsWith rectangle) && rectangle in polygon

        onRectangleChecked?.invoke(rectangle, isValid)

        if (isValid && rectangle.surface > (largestValidRectangle?.surface ?: 0L)) {
            largestValidRectangle = rectangle
        }
    }

    return largestValidRectangle!!
}

fun String.parse(): List<Point> = lines().map { line ->
    line
        .split(",")
        .map(String::toLong)
        .let { (x, y) -> Point(x, y) }
}

fun List<Point>.findLargestRectangle(): Rectangle = combinations()
    .map(::Rectangle)
    .reduce { largestRectangle, currentRectangle ->
        if (currentRectangle.surface > largestRectangle.surface) currentRectangle
        else largestRectangle
    }

fun List<Point>.renderFindingLargestValidRectangle(
    insertDelay: Boolean = false,
    recordVideo: Boolean = false,
): Rectangle {
    val points = this
    val pointVectors = points.map(Vector2::fromPoint)

    data class CurrentState(
        val rectangle: Rectangle,
        val isValid: Boolean,
    )

    val currentState = AtomicReference<CurrentState?>(null)
    val largestValidRectangle = AtomicReference<Rectangle?>(null)
    val isDone = AtomicBoolean(false)

    application {
        configure {
            width = 1080
            height = 1080
        }

        program {
            launch(Dispatchers.Default) {
                points.findLargestValidRectangle(
                    onRectangleChecked = { rectangle, isValid ->
                        if (insertDelay) {
                            Thread.sleep(0, 150_000)
                        }

                        currentState.set(CurrentState(rectangle, isValid))

                        if (isValid && rectangle.surface > (largestValidRectangle.get()?.surface ?: 0L)) {
                            largestValidRectangle.set(rectangle)
                        }
                    },
                )
                isDone.set(true)
            }

            val transform = ScaleTransform(pointVectors, width, height, 20.0)

            extend(FPSDisplay())

            val recorder = extend(ScreenRecorder()) {
                frameClock = true
                frameRate = 120
                outputToVideo = recordVideo
            }

            extend {
                drawer.isolated {
                    translate(transform.offsetX, transform.offsetY)
                    scale(transform.scale)

                    fill = ColorRGBa(0.05, 0.05, 0.05, 0.1)
                    stroke = GRAY
                    strokeWeight = 1 / transform.scale
                    contour(ShapeContour.fromPoints(pointVectors, true))

                    val currentLargestRectangle = largestValidRectangle.get()
                    if (currentLargestRectangle != null) {
                        fill = ColorRGBa(1.0, 0.1, 1.0, 0.01)
                        stroke = MAGENTA
                        strokeWeight = 1.0 / transform.scale
                        contour(ShapeContour.fromPoints(currentLargestRectangle.vectors, true))
                    }

                    if (!isDone.get()) {
                        val (rectangle, isValid) = currentState.get() ?: return@isolated
                        fill = ColorRGBa(0.1, 0.1, 0.01, 0.1)
                        stroke = if (isValid) GREEN else RED
                        strokeWeight = 1 / transform.scale
                        contour(ShapeContour.fromPoints(rectangle.points.map(Vector2::fromPoint), true))
                    }

                    fill = WHITE
                    stroke = TRANSPARENT
                    circles(pointVectors, 2.0 / transform.scale)
                }

                if (isDone.get()) {
                    recorder.outputToVideo = false
                    application.exit()
                }
            }

            keyboard.keyDown.listen {
                when {
                    it.key == KEY_ESCAPE -> program.application.exit()
                }
            }
        }
    }

    return largestValidRectangle.get()!!
}
