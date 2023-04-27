package connectfour

import kotlin.system.exitProcess

const val DOUBLE_VERTICAL = 0x2551.toChar()
const val LEFT_BOTTOM_ANGLE = 0x255A.toChar()
const val RIGHT_BOTTOM_ANGLE = 0x255D.toChar()
const val DOUBLE_HORIZONTAL = 0x2550.toChar()
const val DOUBLE_UP_AND_HORIZONTAL = 0x2569.toChar()

const val FULL_BOARD = "Full board"
const val WINNER = "Winner"
const val PROCEED = "Proceed"

fun main() {
    println("Connect Four")
    println("First player's name:")
    val firstPlayer = Player(readln(), 0)
    println("Second player's name:")
    val secondPlayer = Player(readln(), 0)

    printDimensionsInputText()
    var boardDimensionsInput = readln()

    val rows: Int
    val columns: Int

//    Inputting board size
    while (true) {
        if (boardDimensionsInput.isEmpty()) {
            rows = 6
            columns = 7
            break
        } else {
            val regex = "\\s*\\d+\\s*[xX]\\s*\\d+\\s*".toRegex()
            if (!regex.matches(boardDimensionsInput)) {
                println("Invalid input")
                printDimensionsInputText()
                boardDimensionsInput = readln()
            } else {
                val params = boardDimensionsInput.split("[xX]".toRegex()).map(String::trim)
                boardDimensionsInput = if (params[0].toInt() !in 5..9) {
                    println("Board rows should be from 5 to 9")
                    printDimensionsInputText()
                    readln()
                } else if (params[1].toInt() !in 5..9) {
                    println("Board columns should be from 5 to 9")
                    printDimensionsInputText()
                    readln()
                } else {
                    rows = params[0].toInt()
                    columns = params[1].toInt()
                    break
                }
            }
        }
    }

    val game = askNumberOfGames(firstPlayer, secondPlayer, columns, rows)

    println(
        """${firstPlayer.name} VS ${secondPlayer.name}
        |$rows X $columns board
    """.trimMargin()
    )

    if (game.numberOfGames == 1) println("Single game") else println("Total ${game.numberOfGames} games")

    repeat(game.numberOfGames) {

        if (game.numberOfGames != 1) println("Game #${game.gameNumber}")

        game.drawGameBoard()
        game.playOneGame()

        if (game.numberOfGames != 1) {
            println("Score")
            println("${firstPlayer.name}: ${firstPlayer.score} ${secondPlayer.name}: ${secondPlayer.score}")
        }

    }
    println("Game over!")
}

private fun checkWinningCondition(gameBoard: MutableList<MutableList<Char>>): String {

    rowLoop@ for (row in gameBoard) {
        for (char in row) {
            if (char == ' ') break@rowLoop
        }
        return FULL_BOARD
    }


    for (rowIndex in gameBoard.indices) {
        for (columnIndex in gameBoard[0].indices) {
            val targetElement = gameBoard[rowIndex][columnIndex]
            if (targetElement == ' ') continue

//            Row checking
            if (columnIndex != gameBoard[0].lastIndex) {
                var counter = 1
                for (rowElement in gameBoard[rowIndex].subList(columnIndex + 1, gameBoard[rowIndex].lastIndex)) {
                    when (rowElement) {
                        targetElement -> counter++
                        else -> break
                    }
                    if (counter == 4) return WINNER
                }
            }

//            Column checking
            if (rowIndex != gameBoard.lastIndex) {
                var counter = 1
                val columnTarget = gameBoard.subList(rowIndex + 1, gameBoard.lastIndex + 1)
                    .map { it[columnIndex] }.toList()

                for (columnElement in columnTarget) {
                    when (columnElement) {
                        targetElement -> counter++
                        else -> break
                    }
                    if (counter == 4) return WINNER
                }
            }

//            Diagonal checking
            if (gameBoard.lastIndex - rowIndex >= 3 && gameBoard[0].lastIndex - columnIndex >= 3) {
                var counter = 1
                val diagonalTarget = gameBoard.subList(rowIndex + 1, rowIndex + 4)
                    .mapIndexed { index, chars -> chars[columnIndex + (index + 1)] }.toList()

                for (diagonalElement in diagonalTarget) {
                    when (diagonalElement) {
                        targetElement -> counter++
                        else -> break
                    }
                    if (counter == 4) return WINNER
                }
            }

//            Left diagonal checking
            if (gameBoard.lastIndex - rowIndex >= 3 && columnIndex >= 3) {
                var counter = 1
                val diagonalTarget = gameBoard.subList(rowIndex + 1, rowIndex + 4)
                    .mapIndexed { index, chars -> chars[columnIndex - (index + 1)] }.toList()

                for (diagonalElement in diagonalTarget) {
                    when (diagonalElement) {
                        targetElement -> counter++
                        else -> break
                    }
                    if (counter == 4) return WINNER
                }
            }

        }
    }
    return PROCEED
}

private fun printDimensionsInputText() {
    println(
        """Set the board dimensions (Rows x Columns)
        |Press Enter for default (6 x 7)""".trimMargin()
    )
}

private fun createHeaderRow(number: Int) = IntArray(number) { it + 1 }.joinToString(" ", prefix = " ")

private fun createBottomRow(number: Int): String {
    return CharArray(number) { DOUBLE_HORIZONTAL }
        .joinToString(
            separator = DOUBLE_UP_AND_HORIZONTAL.toString(),
            prefix = LEFT_BOTTOM_ANGLE.toString(),
            postfix = RIGHT_BOTTOM_ANGLE.toString()
        )
}

private fun playerStepInput(columns: Int, gameBoard: MutableList<MutableList<Char>>, playerName: String): Int {
    do {
        println("${playerName}'s turn:")
        val regex = "\\d*".toRegex()
        val input = readln()

        when {
            (input == "end") -> {
                println("Game over!")
                exitProcess(0)
            }

            (!regex.matches(input)) -> {
                println("Incorrect column number")
                continue
            }

            (input.toInt() !in 1..columns) -> {
                println("The column number is out of range (1 - $columns)")
                continue
            }

            (gameBoard[input.toInt() - 1].indexOf(' ') == -1) -> {
                println("Column $input is full")
                continue
            }

            else -> return input.toInt()
        }
    } while (true)
}

class Game(
    val numberOfGames: Int,
    var gameNumber: Int,
    private val firstPlayer: Player,
    private val secondPlayer: Player,
    private val rows: Int,
    private val columns: Int,
    private var gameBoard: MutableList<MutableList<Char>> = MutableList(columns) { MutableList(rows) { ' ' } }
) {
    private fun clearGameBoard() {
        gameBoard = MutableList(columns) { MutableList(rows) { ' ' } }
    }

    fun drawGameBoard() {
        println(createHeaderRow(gameBoard.size))
        for (i in gameBoard[0].lastIndex downTo 0) {
            val elements = gameBoard.map { it[i] }.toList()
            println(
                elements.joinToString(
                    DOUBLE_VERTICAL.toString(),
                    DOUBLE_VERTICAL.toString(),
                    DOUBLE_VERTICAL.toString()
                )
            )
        }
        println(createBottomRow(gameBoard.size))
    }

    private fun doPlayerStep(activePlayer: Player) {
        val playerTurn = playerStepInput(columns, gameBoard, activePlayer.name)
        gameBoard[playerTurn - 1][gameBoard[playerTurn - 1].indexOf(' ')] =
            if (activePlayer == firstPlayer) 'o' else '*'
        drawGameBoard()
    }

    fun playOneGame() {
        while (true) {
            val playerWithFirstStep = if (gameNumber % 2 == 1) firstPlayer else secondPlayer
            val playerWithSecondStep = if (gameNumber % 2 == 1) secondPlayer else firstPlayer
            doPlayerStep(playerWithFirstStep)
            if (proceedFinishingGame(checkWinningCondition(gameBoard), playerWithFirstStep)) return
            doPlayerStep(playerWithSecondStep)
            if (proceedFinishingGame(checkWinningCondition(gameBoard), playerWithSecondStep)) return
        }
    }

    private fun proceedFinishingGame(result: String, lastStepPlayer: Player): Boolean {
        when (result) {
            FULL_BOARD -> {
                firstPlayer.score += 1
                secondPlayer.score += 1
                clearGameBoard()
                gameNumber++
                printGameResult(isDraw = true, lastStepPlayer)
                return true
            }

            WINNER -> {
                lastStepPlayer.score += 2
                clearGameBoard()
                gameNumber++
                printGameResult(isDraw = false, lastStepPlayer)
                return true
            }

            else -> return false
        }
    }

    private fun printGameResult(isDraw: Boolean = false, winner: Player) {
        if (isDraw) println("It is a draw") else println("Player ${winner.name} won")
    }
}

data class Player(val name: String, var score: Int)

private fun askNumberOfGames(p1: Player, p2: Player, columns: Int, rows: Int): Game {
    while (true) {
        println(
            """
        Do you want to play single or multiple games?
        For a single game, input 1 or press Enter
        Input a number of games:
    """.trimIndent()
        )
        val numberOfGamesInput = readln()
        val regex = "[1-9]*".toRegex()

        if (!numberOfGamesInput.matches(regex)) {
            println("Invalid input")
            continue
        } else {
            return Game(if (numberOfGamesInput.isEmpty()) 1 else numberOfGamesInput.toInt(), 1, p1, p2, rows, columns)
        }
    }

}


