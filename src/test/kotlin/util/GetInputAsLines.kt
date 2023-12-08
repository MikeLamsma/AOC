package util

import java.io.FileNotFoundException

fun getInputAsLines(year: String, day: String) = object {}::class.java
    .getResourceAsStream("/inputs/y$year/$day.txt")
    ?.bufferedReader()
    ?.readLines() ?: throw FileNotFoundException()

fun getInputAsString(year: String, day: String) = object {}::class.java
    .getResourceAsStream("/inputs/y$year/$day.txt")
    ?.bufferedReader()
    ?.readText() ?: throw FileNotFoundException()
