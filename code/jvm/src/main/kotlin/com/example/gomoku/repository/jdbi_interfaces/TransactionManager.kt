package com.example.gomoku.repository.jdbi_interfaces


interface TransactionManager {
    fun <R> run(block: (Transaction) -> R): R
}