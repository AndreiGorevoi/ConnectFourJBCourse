fun main() {
    val number = readln().toInt()

    when(number) {
        2 -> println("Yes!")
        in 1..4 -> println("No!")
        else -> println("Unknown number")
    }
}
