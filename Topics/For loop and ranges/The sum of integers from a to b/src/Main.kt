fun main() {
    val firstNumber = readln().toInt()
    val secondNumber = readln().toInt()
    var result = 0
    for (i in firstNumber..secondNumber) {
        result += i
    }
    println(result)
}