fun main() {
    val word = readln()
    val listOfVowels = listOf('a', 'e', 'i', 'o', 'u', 'y')

    var prevCharIsVowel = listOfVowels.contains(word[0])
    var repeatingLength = 1
    var result = 0

    for (index in 1..word.lastIndex) {
        val isVowel = listOfVowels.contains(word[index])
        if (prevCharIsVowel && isVowel || !prevCharIsVowel && !isVowel) {
            repeatingLength++
        }
        if (prevCharIsVowel && !isVowel || !prevCharIsVowel && isVowel || index == word.lastIndex) {
            if (repeatingLength >= 3) {
                result += if (repeatingLength % 2 == 0) (repeatingLength - 1) / 2 else repeatingLength / 2
            }
            repeatingLength = 1
        }
        prevCharIsVowel = listOfVowels.contains(word[index])
    }

    println(result)
}
