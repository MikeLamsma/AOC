package y2023

import util.getInputAsLines
import util.parseExampleInput
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

private fun task1(inputLines: List<String>) =
    inputLines
        .sumOf { card ->
            val numbers = parseNumbersFromCard(card)
            val (winningNumbers, scratchedNumbers) = pairOfNumbers(numbers)

            var points = 0
            winningNumbers.forEach { winningNumber ->
                if (scratchedNumbers.contains(winningNumber)) {
                    if (points == 0) {
                        points++
                    } else {
                        points *= 2
                    }
                }
            }
            points
        }

private fun task2(inputLines: List<String>) =
    inputLines
        .sumOf {
            score(inputLines, it)
        }

private fun score(inputLines: List<String>, card: String): Int {
    val cardNumber = "[0-9]+".toRegex().find(card)?.value?.toInt() ?: throw Error()
    val numbers = parseNumbersFromCard(card)
    val (winningNumbers, scratchedNumbers) = pairOfNumbers(numbers)

    var matches = 0
    winningNumbers.forEach { winningNumber ->
        if (scratchedNumbers.contains(winningNumber)) {
            matches++
        }
    }

    var score = 1

    for (match in 1..matches) {
        score += score(inputLines, inputLines[cardNumber - 1 + match])
    }

    return score
}

private fun parseNumbersFromCard(card: String) = card.substringAfter(": ").trim().replace("\\s+".toRegex(), " ").split(" | ")

private fun pairOfNumbers(numbers: List<String>) =
    parseNumbers(numbers.first()) to parseNumbers(numbers.last())

private fun parseNumbers(numbersString: String) = numbersString.split(" ").map { it.toInt() }

class Day04 {
    private val input = getInputAsLines("2023", this::class.simpleName!!.lowercase(Locale.getDefault()))

    @Test
    fun testTask1ExampleInput() {
        val expected = 13
        val input = parseExampleInput(exampleInput)
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask1ActualInput() {
        val expected = 27059
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask2ExampleInput() {
        val expected = 30
        val input = parseExampleInput(exampleInput)
        assertEquals(expected, task2(input))
    }

    @Test
    fun testTask2ActualInput() {
        val expected = 5744979
        assertEquals(expected, task2(input))
    }
}

private val exampleInput =
    """
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
    """.trimIndent()
