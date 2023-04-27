fun main() {
    val a = readln().toInt()
    val b = readln().toInt()
    var result: Long = 1

    for (i in a until b) {
        result*=i
    }
    println(result)
}
