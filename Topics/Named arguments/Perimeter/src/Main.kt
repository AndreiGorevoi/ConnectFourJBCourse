import kotlin.math.abs
import kotlin.math.hypot

fun perimeter(
    x1: Double,
    y1: Double,
    x2: Double,
    y2: Double,
    x3: Double,
    y3: Double,
    x4: Double = x3,
    y4: Double = y3
): Double {
    return hypot(abs(x2 - x1), abs(y2 - y1)) + hypot(abs(x3 - x2), abs(y3 - y2))
    + hypot(abs(x4 - x3), abs(y4 - y3))
    +hypot(abs(x4 - x1), abs(y4 - y1))
}