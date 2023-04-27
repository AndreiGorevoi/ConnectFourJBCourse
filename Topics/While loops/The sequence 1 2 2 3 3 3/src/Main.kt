fun main() {
    val numbers = readln().toInt()
    val result = mutableListOf<Int>()
    var currentDigit = 1



    while (result.size < numbers) {
        if (result.filter { it == currentDigit }.size == currentDigit) {
            currentDigit++
        }
        result.add(currentDigit)
    }
    println(result.joinToString(" "))
}