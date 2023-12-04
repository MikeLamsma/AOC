package y2023

import util.getInputAsLines
import util.parseExampleInput
import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals

private fun task1(inputLines: List<String>): Int {
    var sumOfIds = 0
    inputLines
        .forEachIndexed { index, game ->
            game
                .substringAfter(": ")
                .split("; ")
                .flatMap { set ->
                    set
                        .split(", ")
                        .map { amountOfColorDie ->
                            val amountToColor = amountOfColorDie.split(" ").let { it.first().toInt() to it.last() }
                            when (amountToColor.second) {
                                "red" -> amountToColor.first <= 12
                                "green" -> amountToColor.first <= 13
                                "blue" -> amountToColor.first <= 14
                                else -> throw IllegalStateException()
                            }
                        }
                }
                .let { isPossibleList ->
                    if (isPossibleList.all { it }) sumOfIds += (index + 1)
                }
        }
    return sumOfIds
}

private fun task2(inputLines: List<String>) =
    inputLines
        .sumOf { game ->
            var highestRed = 0
            var highestGreen = 0
            var highestBlue = 0
            game
                .substringAfter(": ")
                .split("; ")
                .forEach { set ->
                    set
                        .split(", ")
                        .map { amountOfColorDie ->
                            val amountToColor = amountOfColorDie.split(" ").let { it.first().toInt() to it.last() }
                            when (amountToColor.second) {
                                "red" ->
                                    highestRed =
                                        if (amountToColor.first > highestRed) amountToColor.first else highestRed

                                "green" ->
                                    highestGreen =
                                        if (amountToColor.first > highestGreen) amountToColor.first else highestGreen

                                "blue" ->
                                    highestBlue =
                                        if (amountToColor.first > highestBlue) amountToColor.first else highestBlue

                                else -> throw IllegalStateException()
                            }
                            amountToColor
                        }
                }
            highestRed * highestGreen * highestBlue
        }

class Day02 {
    private val input = getInputAsLines("2023", this::class.simpleName!!.lowercase(Locale.getDefault()))

    @Test
    fun testTask1ExampleInput() {
        val expected = 8
        val input = parseExampleInput(exampleInput1)
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask1ActualInput() {
        val expected = 2101
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask2ExampleInput() {
        val expected = 2286
        val input = parseExampleInput(exampleInput2)
        assertEquals(expected, task2(input))
    }

    @Test
    fun testTask2ActualInput() {
        val expected = 58269
        assertEquals(expected, task2(input))
    }
}

private val exampleInput1 =
    """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent()

private val exampleInput2 =
    """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent()
