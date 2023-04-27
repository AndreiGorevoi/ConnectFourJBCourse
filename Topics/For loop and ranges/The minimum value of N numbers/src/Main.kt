fun main() {
    val intArray = IntArray(readln().toInt()) { readln().toInt() }
    println(intArray.minOrNull())
}
