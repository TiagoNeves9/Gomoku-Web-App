package com.example.gomoku.http

import com.example.gomoku.http.model.LinkRelation
import org.springframework.web.util.UriTemplate
import java.net.URI
import java.util.*


object PathTemplate {
    const val HOME = "/"

    const val START = "/games/start"
    const val LOBBIES = "/lobbies"
    const val LEAVE_LOBBY = "/lobbies/leave"
    const val JOIN_LOBBY = "/lobbies/join"
    const val GAMES = "/games"
    const val PLAY = "/games/{id}"
    const val SPECTATE = "/games/spectate/{id}"
    const val IS_GAME_CREATED = "/lobbies/{id}"
    const val GAME_BY_ID = "/games/{id}"
    const val LATEST_GAME = "/games/game"
    const val LATEST_EXIST = "/lobbies/game"

    const val USER = "/user"
    const val USERS = "/users"
    const val USER_BY_ID = "/users/{id}"
    const val CREATE_USER = "/users/signup"
    const val LOGIN = "/users/login"
    const val COOKIE = "/users/cookie"

    const val RANKINGS = "/rankings"
    const val USER_RANKING = "/rankings/{username}"

    const val AUTHORS = "/authors"
    const val ABOUT = "/about"

    fun home(): URI = URI(HOME)
    fun start(): URI = URI(START)
    fun joinLobby(): URI = URI(JOIN_LOBBY)
    fun play(gameId: UUID): URI = UriTemplate(PLAY).expand(gameId)
    fun isGameCreated(lobbyId: UUID): URI = UriTemplate(IS_GAME_CREATED).expand(lobbyId)
    fun gameById(gameId: UUID): URI = UriTemplate(GAME_BY_ID).expand(gameId)
}

object LinkRelations {
    val HOME = LinkRelation("home")
    val LOBBY = LinkRelation("lobby")
    val SELF = LinkRelation("self")
    val GAME = LinkRelation("current-game")
}