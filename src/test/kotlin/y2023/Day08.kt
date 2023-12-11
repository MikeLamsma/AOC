package y2023

import util.getInputAsLines
import util.parseExampleInput
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

data class Node(val left: String, val right: String)

private fun task1(input: List<String>): Int {
    val nodes = mutableMapOf<String, Node>()
    input.drop(2).forEach {
        val node = it.substringAfter("= ").removeSurrounding("(", ")").split(", ").let { Node(it.first(), it.last()) }
        nodes[it.substringBefore(" =")] = node
    }

    var steps = 0
    val instructions = input.first()
    var currentInstructionsIndex = 0
    var currentNode = "AAA"
    while (currentNode != "ZZZ") {
        if (currentInstructionsIndex == instructions.length) {
            currentInstructionsIndex = 0
        }
        when (instructions[currentInstructionsIndex]) {
            'L' -> currentNode = nodes[currentNode]?.left ?: throw Exception()
            'R' -> currentNode = nodes[currentNode]?.right ?: throw Exception()
        }
        currentInstructionsIndex++
        steps++
    }

    return steps
}

private fun task2(input: List<String>): Long {
    val nodes = mutableMapOf<String, Node>()
    input.drop(2).forEach {
        val node = it.substringAfter("= ").removeSurrounding("(", ")").split(", ").let { Node(it.first(), it.last()) }
        nodes[it.substringBefore(" =")] = node
    }

    var steps = 0L
    val instructions = input.first()
    var currentInstructionsIndex = 0
    var currentNodes = nodes.filter { it.key.endsWith("A") }.toList()
    while (!currentNodes.all { it.first.endsWith("Z") }) {
        if (currentInstructionsIndex == instructions.length) {
            currentInstructionsIndex = 0
        }
        when (instructions[currentInstructionsIndex]) {
            'L' -> currentNodes = currentNodes.map {
                val next = nodes[it.first]!!.left; next to nodes[next]!!
            }

            'R' -> currentNodes = currentNodes.map {
                val next = nodes[it.first]!!.right; next to nodes[next]!!
            }
        }
        currentInstructionsIndex++
        steps++
    }

    return steps
}

class Day08 {
    private val input = getInputAsLines("2023", this::class.simpleName!!.lowercase(Locale.getDefault()))

    @Test
    fun testTask1ExampleInput() {
        val expected = 6
        val input = parseExampleInput(exampleInput1)
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask1ActualInput() {
        val expected = 18727
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask2ExampleInput() {
        val expected = 6L
        val input = parseExampleInput(exampleInput2)
        assertEquals(expected, task2(input))
    }

    @Test
    fun testTask2ActualInput() {
        val expected = 0L
        assertEquals(expected, task2(input))
    }
}

private val exampleInput1 =
    """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

private val exampleInput2 =
    """
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
    """.trimIndent()
