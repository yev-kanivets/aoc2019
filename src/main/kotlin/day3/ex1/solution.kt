package day3.ex1

import PATH_PREFIX
import java.io.File
import kotlin.math.absoluteValue

data class Position(
    val x: Int,
    val y: Int
)

sealed class Segment {

    data class Horizontal(
        val y: Int,
        val xLeft: Int,
        val xRight: Int
    ) : Segment()

    data class Vertical(
        val x: Int,
        val yTop: Int,
        val yBottom: Int
    ) : Segment()
}

fun main() {
    val input = File("$PATH_PREFIX/day3/ex1/input.txt").readLines()
    val firstWire = input[0].split(",")
    val secondWire = input[1].split(",")

    val firstWireSegments = calculateSegments(firstWire)
    val secondWireSegments = calculateSegments(secondWire)

    val intersections = firstWireSegments.flatMap { firstSegment ->
        secondWireSegments.map { secondSegment -> intersection(firstSegment, secondSegment) }
    }.filterNotNull().filterNot { it.x == 0 && it.y == 0 }

    val minDistance = intersections.map { it.x.absoluteValue + it.y.absoluteValue }.min()

    println(minDistance)
}

private fun calculateSegments(wire: List<String>): List<Segment> {
    val segments = mutableListOf<Segment>()
    var position = Position(0, 0)

    wire.forEach {
        val direction = it[0]
        val shift = it.substring(1).toInt()

        position = when (direction) {
            'U' -> {
                segments.add(
                    Segment.Vertical(
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
                        y = position.y,
                        xLeft = position.x,
                        xRight = position.x + shift
                    )
                )
                position.copy(x = position.x + shift)
            }
            else -> position
        }
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
            ) {
                Position(segment1.x, segment2.y)
            } else {
                null
            }
        }
        segment1 is Segment.Horizontal && segment2 is Segment.Vertical -> {
            if (segment2.x >= segment1.xLeft
                && segment2.x <= segment1.xRight
                && segment1.y >= segment2.yTop
                && segment1.y <= segment2.yBottom
            ) {
                Position(segment2.x, segment1.y)
            } else {
                null
            }
        }
        else -> null
    }
}