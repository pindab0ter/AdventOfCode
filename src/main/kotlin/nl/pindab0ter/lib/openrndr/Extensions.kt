package nl.pindab0ter.lib.openrndr

import nl.pindab0ter.lib.types.Point
import org.openrndr.math.Vector2

fun Vector2.Companion.fromPoint(point: Point): Vector2 = Vector2(point.x.toDouble(), point.y.toDouble())
