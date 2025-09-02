package me.i18u.database

import java.util.Scanner

interface QueryEngine {
    fun doQuery(sql: String): TableDto
    fun doUpdate(sql: String): TableDto
}

enum class TokenType {
    KEYWORD,
    NAME,
    WILDCARD,
    OPERATOR,
    FLOAT,
    INTEGER,
    COMMA,
    TERMINAL,
    UNKNOWN
}

data class Token(val value: String, val type: TokenType)

class Lexer {
    private val keywords: List<String> = listOf("select", "where", "from", "and", "or", "not", "update", "delete", "create")
    private val operators: List<String> = listOf("<", "<=", ">", ">=", "=")

    fun isKeyword(str: String): Boolean {
        return keywords.contains(str)
    }

    fun isName(str: String): Boolean {
        return str.isNotEmpty() && str[0].isLetter() && str.all { it.isLetter() || it.isDigit() || it == '_' }
    }

    fun isWildcard(str: String): Boolean {
        return str == "*"
    }

    fun isOperator(str: String): Boolean {
        return operators.contains(str)
    }

    fun isFloat(str: String): Boolean {
        var index = 0;
        var dotsAllowed = 1;

        while (index < str.length) {
            var char = str[index]
            index++

            if (char == '.') {
                if (dotsAllowed != 0) {
                    dotsAllowed--
                } else {
                    return false
                }
            }
            if (!char.isDigit()) return false
        }

        return true
    }

    fun isInteger(str: String): Boolean {
        return str.all { it.isDigit() }
    }

    fun isComma(str: String): Boolean {
        return str == ","
    }

    fun isTerminal(str: String): Boolean {
        return str == ";"
    }

    fun tokenise(input: String): Pair<List<Token>, List<Token>> {
        val knownTokens = mutableListOf<Token>()
        val unknownTokens = mutableListOf<Token>()

        var index = 0

        while (index < input.length) {
            var char = input[index]

            // Eat whitespace
            while (char.isWhitespace()) {
                char = input[++index]
            }

            var lowerToken = ""
            var token = ""

            if (char == ';' || char == ',') {
                lowerToken += char.lowercase()
                token += char
                index++
            } else {
                while (!char.isWhitespace() && char != ';' && char != ',') {
                    lowerToken += char.lowercase()
                    token += char
                    char = input[++index]
                }
            }

            if (isKeyword(lowerToken)) {
                knownTokens.add(Token(token, TokenType.KEYWORD))
            } else if (isName(lowerToken)) {
                knownTokens.add(Token(token, TokenType.NAME))
            } else if (isWildcard(lowerToken)) {
                knownTokens.add(Token(token, TokenType.WILDCARD))
            } else if (isOperator(lowerToken)) {
                knownTokens.add(Token(token, TokenType.OPERATOR))
            } else if (isFloat(lowerToken)) {
                knownTokens.add(Token(token, TokenType.FLOAT))
            } else if (isInteger(lowerToken)) {
                knownTokens.add(Token(token, TokenType.INTEGER))
            } else if (isComma(lowerToken)) {
                knownTokens.add(Token(token, TokenType.COMMA))
            } else if (isTerminal(lowerToken)) {
                knownTokens.add(Token(token, TokenType.TERMINAL))
            } else {
                unknownTokens.add(Token(token, TokenType.UNKNOWN))
            }
        }

        return Pair(knownTokens, unknownTokens)

    }
}

class QueryEngineImpl : QueryEngine {
    override fun doQuery(sql: String): TableDto {
        TODO("Not yet implemented")
    }

    override fun doUpdate(sql: String): TableDto {
        TODO("Not yet implemented")
    }

}

data class TableDto(val example: String)

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