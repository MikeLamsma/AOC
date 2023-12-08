package y2023

import util.getInputAsString
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

data class AlmanacMap(val destination: Long, val source: Long, val range: Long)

private fun task1(input: String): Long {
    val (seeds, maps) = extractSeedsAndMaps(input)

    return seeds.minOf { seed ->
        getLocation(seed, maps)
    }
}

private fun task2(input: String): Long {
    val (seeds, maps) = extractSeedsAndMaps(input)
    var lowest = Long.MAX_VALUE
    seeds.chunked(2).forEach {
        (it.first()..<(it.first() + it.last())).forEach { seed ->
            val location = getLocation(seed, maps)
            if (location < lowest) {
                lowest = location
            }
        }
    }

    return lowest
}

private fun extractSeedsAndMaps(input: String): Pair<List<Long>, List<List<AlmanacMap>>> {
    var seeds = emptyList<Long>()
    val maps = mutableListOf<List<AlmanacMap>>()
    input.split("\n\n").map {
        if (it.startsWith("seeds: ")) {
            seeds = it.substringAfter(": ").split(" ").map { it.toLong() }
        } else {
            val map = it.split("\n").toMutableList()
            map.removeFirst()
            maps.add(
                map.map {
                    val numbers = it.split(" ").map { it.toLong() }
                    AlmanacMap(numbers[0], numbers[1], numbers[2])
                },
            )
        }
    }
    return seeds to maps
}

private fun getLocation(seed: Long, maps: List<List<AlmanacMap>>): Long {
    var next = seed
    maps.forEach {
        it.filter { almanacMap -> next in almanacMap.source..<(almanacMap.source + almanacMap.range) }
            .forEach { almanacMap ->
                next = next - almanacMap.source + almanacMap.destination
            }
    }
    return next
}

class Day05 {
    private val input = getInputAsString("2023", this::class.simpleName!!.lowercase(Locale.getDefault()))

    @Test
    fun testTask1ExampleInput() {
        val expected = 35L
        assertEquals(expected, task1(exampleInput))
    }

    @Test
    fun testTask1ActualInput() {
        val expected = 486613012L
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask2ExampleInput() {
        val expected = 46L
        assertEquals(expected, task2(exampleInput))
    }

    @Test
    fun testTask2ActualInput() {
        val expected = 56931769L
        assertEquals(expected, task2(input))
    }
}

private val exampleInput =
    """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent()
