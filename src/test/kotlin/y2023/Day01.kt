package y2023

import util.getInputAsLines
import util.parseExampleInput
import kotlin.test.Test
import kotlin.test.assertEquals

private fun task1(inputLines: List<String>) =
    inputLines
        .sumOf { string ->
            string.filter { char ->
                char.isDigit()
            }.let { digits ->
                "${digits.first()}${digits.last()}".toInt()
            }
        }

private fun task2(inputLines: List<String>) =
    task1(
        inputLines
            .map {
                it.replace("one", "o1e")
                    .replace("two", "t2o")
                    .replace("three", "t3e")
                    .replace("four", "f4r")
                    .replace("five", "f5e")
                    .replace("six", "s6x")
                    .replace("seven", "s7n")
                    .replace("eight", "e8t")
                    .replace("nine", "n9e")
                    .replace("zero", "z0o")
            },
    )

val input = getInputAsLines("2023", "day01")

class Day01 {
    @Test
    fun testTask1ExampleInput() {
        val expected = 142
        val input = parseExampleInput(exampleInput1)
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask1ActualInput() {
        val expected = 56506
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask2ExampleInput() {
        val expected = 281
        val input = parseExampleInput(exampleInput2)
        assertEquals(expected, task2(input))
    }

    @Test
    fun testTask2ActualInput() {
        val expected = 56017
        assertEquals(expected, task2(input))
    }
}

private val exampleInput1 =
    """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent()

private val exampleInput2 =
    """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """.trimIndent()
