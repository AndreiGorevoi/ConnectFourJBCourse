fun main() {
    val string = readln()
    val result = string.substring(0, string.lastIndexOf('u') + 1) + string.substring(string.lastIndexOf('u') + 1).uppercase()

    println(result)
}