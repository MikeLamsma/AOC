package y2023

import util.getInputAsLines
import util.parseExampleInput
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

private fun task1(input: List<String>): Long {
    val times = "\\d+".toRegex().findAll(input.first().substringAfter(": ")).toList().map { it.value.toLong() }
    val distances = "\\d+".toRegex().findAll(input.last().substringAfter(": ")).toList().map { it.value.toLong() }

    return getTotalWinPossibilities(times, distances)
}

private fun task2(input: List<String>): Long {
    val times = listOf(
        "\\d+".toRegex().findAll(input.first().substringAfter(": ")).toList().map { it.value }
            .reduce { acc: String, number: String -> acc + number }.toLong(),
    )
    val distances = listOf(
        "\\d+".toRegex().findAll(input.last().substringAfter(": ")).toList().map { it.value }
            .reduce { acc: String, number: String -> acc + number }.toLong(),
    )

    return getTotalWinPossibilities(times, distances)
}

private fun getTotalWinPossibilities(
    times: List<Long>,
    distances: List<Long>,
) =
    times.mapIndexed { index, totalRaceTime ->
        val distanceToBeat = distances[index]
        var wins = 0
        for (millisecond in 1..<totalRaceTime) {
            if (millisecond * (totalRaceTime - millisecond) > distanceToBeat) {
                wins++
            }
        }
        wins
    }.reduce { acc: Int, wins: Int -> acc * wins }.toLong()

class Day06 {
    private val input = getInputAsLines("2023", this::class.simpleName!!.lowercase(Locale.getDefault()))

    @Test
    fun testTask1ExampleInput() {
        val expected = 288L
        val input = parseExampleInput(exampleInput)
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask1ActualInput() {
        val expected = 1155175L
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask2ExampleInput() {
        val expected = 71503L
        val input = parseExampleInput(exampleInput)
        assertEquals(expected, task2(input))
    }

    @Test
    fun testTask2ActualInput() {
        val expected = 35961505L
        assertEquals(expected, task2(input))
    }
}

private val exampleInput =
    """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()
