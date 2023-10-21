package com.example.gomoku.repository.jdbi

import com.example.gomoku.repository.Transaction
import com.example.gomoku.repository.TransactionManager
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component


/** This class is responsible for managing JDBI transactions */
@Component
class JdbiTransactionManager(private val jdbi: Jdbi) : TransactionManager {
    /** This method is responsible for running a transaction */
    override fun <R> run(block: (Transaction) -> R): R =
        jdbi.inTransaction<R, Exception> { handle ->
            val transaction = JdbiTransaction(handle)
            block(transaction)
        }
}