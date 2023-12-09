package util

fun inputLineToInteger(inputLine: String) = "-?\\d+".toRegex().findAll(inputLine).toList().map { it.value.toInt() }
