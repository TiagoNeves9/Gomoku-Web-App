package com.example.gomoku

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@SpringBootApplication
class GomokuApplication {
    @Bean
    fun jdbi(): Jdbi {
        val jdbcDatabaseURL =
            System.getenv("JDBC_DATABASE_URL")
                ?: "jdbc:postgresql://localhost/postgres?user=postgres&password=admin"
        val dataSource = PGSimpleDataSource()
        dataSource.setURL(jdbcDatabaseURL)

        return Jdbi.create(dataSource).installPlugin(KotlinPlugin())
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}


fun main(args: Array<String>) {
    runApplication<GomokuApplication>(*args)
}