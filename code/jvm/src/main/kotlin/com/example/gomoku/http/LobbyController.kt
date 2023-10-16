package com.example.gomoku.http

import com.example.gomoku.domain.Lobby
import com.example.gomoku.domain.game.Game
import com.example.gomoku.http.model.BoardOutputModel
import com.example.gomoku.http.model.GameOutputModel
import com.example.gomoku.service.LobbyService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class LobbyController(private val lobbyService: LobbyService) {

    @GetMapping(PathTemplate.DOES_LOBBY_EXIST)
    fun doesLobbyExist() : Boolean {
        return lobbyService.doesLobbyExist()
    }

    @PostMapping(PathTemplate.CREATE_LOBBY)
    fun createLobby(@RequestBody userId: UUID) : Lobby {
        try{
            return lobbyService.createLobby(userId)
        } catch (ex : Exception) {
            throw ex
        }
    }

    @PostMapping(PathTemplate.JOIN_LOBBY)
    fun joinLobby(@RequestBody userId: UUID) : GameOutputModel {
        val game = lobbyService.joinLobbyAndStartGame(userId)
        return GameOutputModel(game.id, BoardOutputModel(game.board.toString()), game.playerB.userId, game.playerW.userId)
    }

}