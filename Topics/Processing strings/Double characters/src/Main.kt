fun main() {
    val str = readln()

    val result = StringBuilder().apply {
        for (ch in str) {
            repeat(2) {
                this.append(ch)
            }
        }
    }.toString()
    println(result)
}
