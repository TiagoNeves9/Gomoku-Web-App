package com.example.gomoku.http

object PathTemplate {
    const val HOME = "/"

    const val CREATE_LOBBY = "/games/lobby"
    const val DOES_LOBBY_EXIST = "/games/lobby"
    const val JOIN_LOBBY = "games/lobby"

    const val START = "/games"
    const val CREATE_GAME = "/games"
    const val GAME_BY_ID = "/games/{id}"
    const val IS_GAME_CREATED = "/games/lobby/{id}"
    const val PLAY = "games/{id}"

    const val USERS = "/users"
    const val USER_BY_ID = "/users/{id}"
    const val USER_BY_USERNAME = "/users/{username}"
    const val CREATE_USER = "/users/signup"
    const val LOGIN = "/users/login"

    const val RANKINGS = "/rankings"
    const val USER_RANKING = "/rankings/{username}"

    const val AUTHOR_INFO = "/authors"
}