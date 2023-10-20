package com.example.gomoku.domain


data class Rules(val boardDim: Int, val opening: Opening, val variant: Variant)

enum class Opening {
    FREESTYLE, PRO
}

fun String.toOpening(): Opening =
    when (this.uppercase()) {
        "FREESTYLE" -> Opening.FREESTYLE
        "PRO" -> Opening.PRO
        else -> throw Exception()
    }

enum class Variant {
    FREESTYLE, SWAP_AFTER_FIRST
}

fun String.toVariant(): Variant =
    when (this.uppercase()) {
        "FREESTYLE" -> Variant.FREESTYLE
        "SWAP_AFTER_FIRST" -> Variant.SWAP_AFTER_FIRST
        else -> throw Exception()
    }