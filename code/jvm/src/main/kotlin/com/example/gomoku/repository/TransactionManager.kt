package com.example.gomoku.repository


interface TransactionManager {
    fun <R> run(block: (Transaction) -> R): R
}