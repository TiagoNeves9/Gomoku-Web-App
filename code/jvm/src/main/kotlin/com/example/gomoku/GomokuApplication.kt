package com.example.gomoku

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class GomokuApplication


fun main(args: Array<String>) {
	runApplication<GomokuApplication>(*args)
}
