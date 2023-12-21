package y2023

import util.getInputAsLines
import util.parseExampleInput
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

val symbolRegex = "[^.0-9]+".toRegex()

fun String.substringWithOneExtraLeftAndRightIndices(indices: IntRange): String = this.substring(
    (indices.first - 1).coerceAtLeast(0), // + 1 voor links
    (indices.last + 2).coerceAtMost(this.length), // + 1 voor incl en + 1 voor rechts
)

private fun task1(inputLines: List<String>): Int {
    var score = 0
    inputLines.forEachIndexed { y, inputLine ->
        "\\d+".toRegex().findAll(inputLine)
            .forEach {
                for (lineIndex in (y - 1).coerceAtLeast(0)..(y + 1).coerceAtMost(inputLines.size - 1)) {
                    if (inputLines[lineIndex].substringWithOneExtraLeftAndRightIndices(it.range)
                            .contains(symbolRegex)
                    ) {
                        score += it.value.toInt()
                    }
                }
            }
    }

    return score
}

private fun task2(inputLines: List<String>): Int {
    return 2
}

class Day03 {
    private val input = getInputAsLines("2023", this::class.simpleName!!.lowercase(Locale.getDefault()))

    @Test
    fun testTask1ExampleInput() {
        val expected = 4361
        val input = parseExampleInput(exampleInput1)
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask1ActualInput() {
        val expected = 533784
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask2ExampleInput() {
        val expected = 0
        val input = parseExampleInput(exampleInput2)
        assertEquals(expected, task2(input))
    }

    @Test
    fun testTask2ActualInput() {
        val expected = 0
        assertEquals(expected, task2(input))
    }
}

private val exampleInput1 =
    """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598..
    """.trimIndent()

private val exampleInput2 =
    """
        
    """.trimIndent()
