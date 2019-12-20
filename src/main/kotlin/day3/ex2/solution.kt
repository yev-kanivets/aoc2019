package day3.ex2

import PATH_PREFIX
import java.io.File
import kotlin.math.absoluteValue

data class Position(
    val x: Int,
    val y: Int
)

sealed class Segment {

    data class Horizontal(
        val direction: Boolean,
        val steps: Int,
        val y: Int,
        val xLeft: Int,
        val xRight: Int
    ) : Segment()

    data class Vertical(
        val direction: Boolean,
        val steps: Int,
        val x: Int,
        val yTop: Int,
        val yBottom: Int
    ) : Segment()
}

fun main() {
    val input = File("$PATH_PREFIX/day3/ex2/input.txt").readLines()
    val firstWire = input[0].split(",")
    val secondWire = input[1].split(",")

    val firstWireSegments = calculateSegments(firstWire)
    val secondWireSegments = calculateSegments(secondWire)

    val intersections = firstWireSegments.flatMap { firstSegment ->
        secondWireSegments.map { secondSegment -> intersection(firstSegment, secondSegment) }
    }.filterNotNull()

    val minSteps = intersections.map { it.x.absoluteValue + it.y.absoluteValue }.min()

    println(minSteps)
}

private fun calculateSegments(wire: List<String>): List<Segment> {
    val segments = mutableListOf<Segment>()
    var position = Position(0, 0)
    var steps = 0

    wire.forEach { command ->
        val direction = command[0]
        val shift = command.substring(1).toInt()

        position = when (direction) {
            'U' -> {
                segments.add(
                    Segment.Vertical(
                        direction = false,
                        steps = steps,
                        x = position.x,
                        yTop = position.y - shift,
                        yBottom = position.y
                    )
                )
                position.copy(y = position.y - shift)
            }
            'L' -> {
                segments.add(
                    Segment.Horizontal(
                        direction = false,
                        steps = steps,
                        y = position.y,
                        xLeft = position.x - shift,
                        xRight = position.x
                    )
                )
                position.copy(x = position.x - shift)
            }
            'D' -> {
                segments.add(
                    Segment.Vertical(
                        direction = true,
                        steps = steps,
                        x = position.x,
                        yTop = position.y,
                        yBottom = position.y + shift
                    )
                )
                position.copy(y = position.y + shift)
            }
            'R' -> {
                segments.add(
                    Segment.Horizontal(
                        direction = true,
                        steps = steps,
                        y = position.y,
                        xLeft = position.x,
                        xRight = position.x + shift
                    )
                )
                position.copy(x = position.x + shift)
            }
            else -> position
        }

        steps += shift
    }

    return segments
}

private fun intersection(segment1: Segment, segment2: Segment): Position? {
    return when {
        segment1 is Segment.Vertical && segment2 is Segment.Horizontal -> {
            if (segment1.x >= segment2.xLeft
                && segment1.x <= segment2.xRight
                && segment2.y >= segment1.yTop
                && segment2.y <= segment1.yBottom
                && segment1.x != 0
                && segment2.y != 0
            ) {
                Position(
                    segment1.steps + if (segment1.direction) segment2.y - segment1.yTop else segment1.yBottom - segment2.y,
                    segment2.steps + if (segment2.direction) segment1.x - segment2.xLeft else segment2.xRight - segment1.x
                )
            } else {
                null
            }
        }
        segment1 is Segment.Horizontal && segment2 is Segment.Vertical -> {
            if (segment2.x >= segment1.xLeft
                && segment2.x <= segment1.xRight
                && segment1.y >= segment2.yTop
                && segment1.y <= segment2.yBottom
                && segment2.x != 0
                && segment1.y != 0
            ) {
                Position(
                    segment2.steps + if (segment2.direction) segment2.x - segment1.xLeft else segment1.xRight - segment2.x,
                    segment1.steps + if (segment1.direction) segment1.y - segment2.yTop else segment2.yBottom - segment1.y
                )
            } else {
                null
            }
        }
        else -> null
    }
}