package nl.pindab0ter.lib.openrndr

import org.openrndr.math.Vector2

internal data class ScaleTransform(
    val scale: Double,
    val offsetX: Double,
    val offsetY: Double,
) {
    companion object {
        operator fun invoke(
            coordinates: Collection<Vector2>,
            canvasWidth: Int,
            canvasHeight: Int,
            padding: Double,
        ): ScaleTransform {
            val minX = coordinates.minOf { it.x }
            val maxX = coordinates.maxOf { it.x }
            val minY = coordinates.minOf { it.y }
            val maxY = coordinates.maxOf { it.y }

            val dataWidth = maxX - minX
            val dataHeight = maxY - minY
            val availableWidth = canvasWidth - 2 * padding
            val availableHeight = canvasHeight - 2 * padding

            val scale = when {
                dataWidth == 0.0 -> availableHeight / dataHeight
                dataHeight == 0.0 -> availableWidth / dataWidth
                else -> minOf(availableWidth / dataWidth, availableHeight / dataHeight)
            }

            val scaledWidth = dataWidth * scale
            val scaledHeight = dataHeight * scale
            val offsetX = padding + (availableWidth - scaledWidth) / 2.0 - minX * scale
            val offsetY = padding + (availableHeight - scaledHeight) / 2.0 - minY * scale

            return ScaleTransform(scale, offsetX, offsetY)
        }
    }
}
