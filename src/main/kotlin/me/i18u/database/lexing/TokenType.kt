package me.i18u.database.lexing

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