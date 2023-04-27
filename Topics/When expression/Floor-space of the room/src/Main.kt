import kotlin.math.sqrt

fun main() {

    val area: Double = when (readln()) {
        "triangle" -> {
            val a = readln().toDouble()
            val b = readln().toDouble()
            val c = readln().toDouble()
            val s = (a + b + c) / 2

            sqrt((s * (s - a) * (s - b) * (s - c)))
        }

        "rectangle" -> {
            val a = readln().toDouble()
            val b = readln().toDouble()
            (a * b)
        }

        "circle" -> {
            val r = readln().toDouble()
            3.14 * r * r
        }

        else -> 0.toDouble()
    }
    println(area)
}