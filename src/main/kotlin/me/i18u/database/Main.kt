package me.i18u.database

import me.i18u.database.lexing.Lexer

fun main() {
    val input = "SELECT * FROM example_table WHERE value = 1;"

    val lexer = Lexer()

    val (knownTokens, unknownTokens) = lexer.tokenise(input)

    println("Known tokens:")

    if (knownTokens.isEmpty()) {
        println("  No known tokens")
    }

    for (token in knownTokens) {
        println("  ${token.value}: ${token.type}")
    }

    println("Unknown tokens:")

    if (unknownTokens.isEmpty()) {
        println("  No unknown tokens")
    }

    for (token in unknownTokens) {
        println("  ${token.value}: ${token.type}")
    }
}