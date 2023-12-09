package y2023

import util.getInputAsLines
import util.inputLineToInteger
import util.parseExampleInput
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

private fun extrapolate(sequence: List<Int>, part2: Boolean): Int =
    if (sequence.all { it == 0 }) {
        0
    } else {
        val newSequence = sequence.drop(1).mapIndexed { index, number ->
            number - sequence[index]
        }

        val nextExtrapolated = extrapolate(newSequence, part2)
        if (!part2) {
            sequence.last() + nextExtrapolated
        } else {
            sequence.first() - nextExtrapolated
        }
    }

private fun task1(input: List<String>) = input.sumOf {
    extrapolate(inputLineToInteger(it), false)
}

private fun task2(input: List<String>) = input.sumOf {
    extrapolate(inputLineToInteger(it), true)
}

class Day09 {
    private val input = getInputAsLines("2023", this::class.simpleName!!.lowercase(Locale.getDefault()))

    @Test
    fun testTask1ExampleInput() {
        val expected = 114
        val input = parseExampleInput(exampleInput)
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask1ActualInput() {
        val expected = 1581679977
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask2ExampleInput() {
        val expected = 2
        val input = parseExampleInput(exampleInput)
        assertEquals(expected, task2(input))
    }

    @Test
    fun testTask2ActualInput() {
        val expected = 889
        assertEquals(expected, task2(input))
    }
}

private val exampleInput =
    """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
    """.trimIndent()
