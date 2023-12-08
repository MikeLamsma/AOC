package y2023

import util.getInputAsLines
import util.parseExampleInput
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

enum class HandType {
    HIGH_CARD,
    PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND,
}

val charCardMap1 = mapOf(
    'T' to 10,
    'J' to 11,
    'Q' to 12,
    'K' to 13,
    'A' to 14,
)

val charCardMap2 = mapOf(
    'T' to 10,
    'J' to 1,
    'Q' to 12,
    'K' to 13,
    'A' to 14,
)

data class Hand(val cards: String, val bid: Int, val charCardValueMap: Map<Char, Int>, val part2: Boolean = false) :
    Comparable<Hand> {

    private fun getType(): HandType {
        val uniqueCards = cards.toSet()
        val amountOfJokers = if (part2) cards.count { it == 'J' } else 0

        return when (uniqueCards.size) {
            1 -> HandType.FIVE_OF_A_KIND
            2 -> {
                if (amountOfJokers > 0) {
                    HandType.FIVE_OF_A_KIND
                } else {
                    getSpecificTypeForOccurrence(4, HandType.FOUR_OF_A_KIND, HandType.FULL_HOUSE)
                }
            }

            3 -> {
                val handTypeWithoutJokers = getSpecificTypeForOccurrence(3, HandType.THREE_OF_A_KIND, HandType.TWO_PAIR)
                if (amountOfJokers > 0) {
                    when (handTypeWithoutJokers) {
                        HandType.THREE_OF_A_KIND -> {
                            HandType.FOUR_OF_A_KIND
                        }
                        HandType.TWO_PAIR -> {
                            if (amountOfJokers == 2) {
                                HandType.FOUR_OF_A_KIND
                            } else {
                                HandType.FULL_HOUSE
                            }
                        }
                        else -> {
                            throw Exception()
                        }
                    }
                } else {
                    handTypeWithoutJokers
                }
            }

            4 -> {
                if (amountOfJokers > 0) {
                    HandType.THREE_OF_A_KIND
                } else {
                    HandType.PAIR
                }
            }

            5 -> {
                if (amountOfJokers > 0) {
                    HandType.PAIR
                } else {
                    HandType.HIGH_CARD
                }
            }

            else -> throw Exception()
        }
    }

    private fun getSpecificTypeForOccurrence(occurrence: Int, typeForOccurs: HandType, otherType: HandType): HandType {
        val charsMap = mutableMapOf<Char, Int>()

        cards.forEach {
            charsMap[it] = charsMap.getOrDefault(it, 0) + 1
        }

        return if (charsMap.values.any { it == occurrence }) {
            typeForOccurs
        } else {
            otherType
        }
    }

    private fun compareFirstCard(otherCards: String, cards: String): Int {
        val otherFirst = getDigitFromCards(otherCards)
        val first = getDigitFromCards(cards)

        return when {
            otherFirst > first -> -1
            otherFirst < first -> 1
            otherFirst == first -> {
                compareFirstCard(otherCards.substring(1), cards.substring(1))
            }

            else -> 0
        }
    }

    private fun getDigitFromCards(cards: String): Int {
        val otherFirst = cards.first().let {
            if (!it.isDigit()) {
                charCardValueMap[it] ?: throw Exception()
            } else {
                it.digitToInt()
            }
        }
        return otherFirst
    }

    override operator fun compareTo(other: Hand): Int {
        return when {
            other.getType() > this.getType() -> -1
            other.getType() < this.getType() -> 1
            else -> compareFirstCard(other.cards, cards)
        }
    }
}

private fun task1(input: List<String>): Int {
    val hands = input.map {
        val handString = it.split(" ")
        Hand(handString.first(), handString.last().toInt(), charCardMap1)
    }.sorted()

    return hands.map { it.bid }.reduceIndexed { index, acc, bid -> acc + bid * (index + 1) }
}

private fun task2(input: List<String>): Int {
    val hands = input.map {
        val handString = it.split(" ")
        Hand(handString.first(), handString.last().toInt(), charCardMap2, true)
    }.sorted()

    return hands.map { it.bid }.reduceIndexed { index, acc, bid -> acc + bid * (index + 1) }
}

class Day07 {
    private val input = getInputAsLines("2023", this::class.simpleName!!.lowercase(Locale.getDefault()))

    @Test
    fun testTask1ExampleInput() {
        val expected = 6440
        val input = parseExampleInput(exampleInput)
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask1ActualInput() {
        val expected = 248559379
        assertEquals(expected, task1(input))
    }

    @Test
    fun testTask2ExampleInput() {
        val expected = 5905
        val input = parseExampleInput(exampleInput)
        assertEquals(expected, task2(input))
    }

    @Test
    fun testTask2ActualInput() {
        val expected = 249631254
        assertEquals(expected, task2(input))
    }
}

private val exampleInput =
    """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()
