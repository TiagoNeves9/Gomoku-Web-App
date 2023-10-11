package com.example.gomoku.http.model

import org.springframework.http.MediaType


data class ErrorJsonModel(val type: String, val title: String? = null) {
    companion object {
        val MEDIA_TYPE = MediaType.parseMediaType("application/problem+json")
    }
}