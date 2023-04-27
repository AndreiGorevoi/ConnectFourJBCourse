fun main() {
    val result = mutableListOf<Int>()
    while (true) {
        when (val number = readln().toInt()) {
            0 -> break
            else -> result.add(number)
        }
    }

    println(result.size)
}