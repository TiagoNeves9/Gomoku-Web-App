package com.example.gomoku.http

import com.example.gomoku.domain.*
import com.example.gomoku.domain.board.*
import com.example.gomoku.http.model.*
import com.example.gomoku.http.pipeline.Authenticated
import com.example.gomoku.http.pipeline.AuthenticatedUserArgumentResolver
import com.example.gomoku.service.Exceptions
import com.example.gomoku.service.GomokuService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


const val DEFAULT_SCORE = 100

@RestController
class GamesController(
    private val gomokuService: GomokuService,
    private val usersController: UsersController
) {
    @ExceptionHandler(value = [Exceptions.NotFoundException::class])
    fun exceptionHandler() = ResponseEntity.status(404).body("GAME NOT FOUND")

    @Authenticated
    @PostMapping(PathTemplate.START)
    fun createOrJoinGame(
        request: HttpServletRequest,
        @RequestBody input: GomokuStartInputModel
    ): ResponseEntity<SirenModel<OutputModel>> {
        val aUser =
            request.getAttribute(AuthenticatedUserArgumentResolver.getKey()) as AuthenticatedUser?
                ?: return ResponseEntity.status(401).body(
                    siren(ErrorOutputModel(401, "User not authenticated!")) {}
                )

        check(input.boardDim == BOARD_DIM || input.boardDim == BIG_BOARD_DIM) {
            "Board dimension must be $BOARD_DIM or $BIG_BOARD_DIM!"
        }

        val user = User(aUser.user.userId, aUser.user.username, aUser.user.encodedPassword)
        val rules = Rules(input.boardDim, input.opening.toOpening(), input.variant.toVariant())

        val lobbyOrNull = gomokuService.createOrJoinLobby(rules)
        if (lobbyOrNull == null) {
            // create a lobby
            val lobby = gomokuService.createLobby(user, rules)
            val lobbyModel = LobbyOutputModel(
                lobby.lobbyId,
                lobby.hostUserId,
                lobby.rules.boardDim,
                lobby.rules.opening.toString(),
                lobby.rules.variant.toString()
            )
            return ResponseEntity.status(201).body(
                siren(lobbyModel) {
                    clazz("Lobby")
                    action(
                        "create lobby",
                        PathTemplate.joinLobby(),
                        HttpMethod.POST,
                        "application/x-www-form-urlencoded"
                    ) {
                        textField("create lobby")
                    }
                }
            )
        } else {
            // host user is trying to join the lobby
            if (lobbyOrNull.hostUserId == user.userId) {
                ResponseEntity.status(405).body(
                    siren(
                        ErrorOutputModel(405, "You can't join your own lobby!")
                    ) {}
                )
            }

            // join the unique lobby, start a game and remove the lobby
            gomokuService.deleteLobby(lobbyOrNull)
            val hostUser = usersController.getById(lobbyOrNull.hostUserId)
            Player(hostUser, Turn.BLACK_PIECE)

            val game = gomokuService.createGame(lobbyOrNull, hostUser, user)
            val gameModel = GameOutputModel(
                id = game.gameId,
                userB = game.users.first,
                userW = game.users.second,
                turn = game.currentPlayer.first.username,
                rules = game.rules,
                boardCells = game.board.positions,
                boardState = game.board.typeToString()
            )
            return ResponseEntity.status(201).body(
                siren(gameModel) {
                    clazz("Game")
                    action(
                        "create game and delete lobby",
                        PathTemplate.play(game.gameId),
                        HttpMethod.POST,
                        "application/x-www-form-urlencoded"
                    ) {
                        textField("create game and delete lobby")
                    }
                }
            )
        }
    }

    @GetMapping(PathTemplate.LOBBIES)
    fun getLobbies(): ResponseEntity<SirenModel<OutputModel>> {
        val lobbies = gomokuService.getLobbies()
        return ResponseEntity.status(200).body(
            siren(LobbiesOutputModel(lobbyList = lobbies)) { clazz("Lobbies") }
        )
    }

    @Authenticated
    @DeleteMapping(PathTemplate.LEAVE_LOBBY)
    fun leaveLobby(request: HttpServletRequest): ResponseEntity<SirenModel<OutputModel>> {
        val aUser =
            request.getAttribute(AuthenticatedUserArgumentResolver.getKey()) as AuthenticatedUser?
                ?: return ResponseEntity.status(401).body(
                    siren(ErrorOutputModel(401, "User not authenticated!")) {}
                )

        return try {
            val wasDeleted = gomokuService.deleteUserLobby(aUser.user.userId)
            if (wasDeleted) ResponseEntity.status(200).body(
                siren(MessageOutputModel("Left Lobby")) {}
            )
            else ResponseEntity.status(404).body(
                siren(ErrorOutputModel(404, "User was not in a lobby!")) {}
            )
        } catch (ex: Exception) {
            ResponseEntity.status(400).body(
                siren(ErrorOutputModel(400, ex.message)) {}
            )
        }
    }

    @Authenticated
    @PostMapping(PathTemplate.JOIN_LOBBY)
    fun joinLobby(
        request: HttpServletRequest,
        @RequestBody lobbyOutputModel: LobbyOutputModel
    ): ResponseEntity<SirenModel<OutputModel>> {
        val aUser =
            request.getAttribute(AuthenticatedUserArgumentResolver.getKey()) as AuthenticatedUser?
                ?: return ResponseEntity.status(401).body(
                    siren(ErrorOutputModel(401, "User not authenticated!")) {}
                )

        val hostUser = usersController.getById(lobbyOutputModel.hostUserId)
        val joiningUser = User(aUser.user.userId, aUser.user.username, aUser.user.encodedPassword)
        if (hostUser.userId == joiningUser.userId)
            return ResponseEntity.status(405).body(
                siren(ErrorOutputModel(405, "User can't join his own lobby!")) {}
            )

        val game = gomokuService.createGame(lobbyOutputModel, hostUser, joiningUser)
        val gameModel = GameOutputModel(
            id = game.gameId,
            userB = game.users.first,
            userW = game.users.second,
            turn = game.currentPlayer.first.username,
            rules = game.rules,
            boardCells = game.board.positions,
            boardState = game.board.typeToString()
        )

        gomokuService.deleteLobby(lobbyOutputModel)
        return ResponseEntity.status(200).body(
            siren(gameModel) {
                clazz("Game")
                action(
                    "game created and lobby deleted",
                    PathTemplate.joinLobby(),
                    HttpMethod.POST,
                    "application/x-www-form-urlencoded"
                ) {
                    textField("game created and lobby deleted")
                }
            }
        )
    }

    @GetMapping(PathTemplate.GAMES)
    fun getGames(): List<Game> = gomokuService.getGames()

    @Authenticated
    @PostMapping(PathTemplate.PLAY)
    fun play(
        request: HttpServletRequest,
        @PathVariable("id") gameId: UUID,
        @RequestBody cellInputModel: CellInputModel
    ): ResponseEntity<SirenModel<OutputModel>> {
        val aUser =
            request.getAttribute(AuthenticatedUserArgumentResolver.getKey()) as AuthenticatedUser?
                ?: return ResponseEntity.status(401).body(
                    siren(ErrorOutputModel(401, "User not authenticated!")) {}
                )

        return try {
            val cell = cellInputModel.toCell()
            val updatedGame = gomokuService.play(aUser, gameId, cell)
            val gameModel = GameOutputModel(
                id = updatedGame.gameId,
                userB = updatedGame.users.first,
                userW = updatedGame.users.second,
                turn = updatedGame.currentPlayer.first.username,
                rules = updatedGame.rules,
                boardCells = updatedGame.board.positions,
                boardState = updatedGame.board.typeToString()
            )
            ResponseEntity.status(200).body(
                siren(gameModel) { clazz("Game") }
            )
        } catch (ex: Exceptions.GameDoesNotExistException) {
            ResponseEntity.status(404).body(
                siren(ErrorOutputModel(404, ex.message)) {}
            )
        } catch (ex: Exceptions.NotYourTurnException) {
            ResponseEntity.status(401).body(
                siren(ErrorOutputModel(401, ex.message)) {}
            )
        } catch (ex: Exceptions.PlayNotAllowedException) {
            ResponseEntity.status(405).body(
                siren(ErrorOutputModel(405, ex.message)) {}
            )
        } catch (ex: Exceptions.WrongPlayException) {
            ResponseEntity.status(405).body(
                siren(ErrorOutputModel(405, ex.message)) {}
            )
        } catch (ex: Exception) {
            ResponseEntity.status(400).body(
                siren(ErrorOutputModel(400, ex.message)) {}
            )
        }
    }

    @GetMapping(PathTemplate.SPECTATE)
    fun spectate(@PathVariable("id") gameId: UUID): ResponseEntity<SirenModel<OutputModel>> {
        return try {
            if (gomokuService.isGameCreated(gameId)) {
                val game = gomokuService.getById(gameId)
                val gameModel = GameOutputModel(
                    id = game.gameId,
                    userB = game.users.first,
                    userW = game.users.second,
                    turn = game.currentPlayer.first.username,
                    rules = game.rules,
                    boardCells = game.board.positions,
                    boardState = game.board.typeToString()
                )
                ResponseEntity.status(200).body(
                    siren(gameModel) { clazz("Game") }
                )
            } else ResponseEntity.status(404).body(
                siren(ErrorOutputModel(404, "Game does not exist!")) {}
            )
        } catch (ex: Exceptions.GameDoesNotExistException) {
            ResponseEntity.status(404).body(
                siren(ErrorOutputModel(404, ex.message)) {}
            )
        }
    }

    @Authenticated
    @GetMapping(PathTemplate.IS_GAME_CREATED)
    fun isGameCreated(
        request: HttpServletRequest,
        @PathVariable("id") lobbyId: UUID
    ): ResponseEntity<SirenModel<OutputModel>> {
        request.getAttribute(AuthenticatedUserArgumentResolver.getKey()) as AuthenticatedUser?
            ?: return ResponseEntity.status(401).body(
                siren(ErrorOutputModel(401, "User not authenticated!")) {}
            )

        return try {
            if (gomokuService.isGameCreated(lobbyId)) {
                ResponseEntity.status(200).body(
                    siren(MessageOutputModel("CREATED")) {
                        clazz("Check if game is created!")
                        action(
                            "game",
                            PathTemplate.gameById(lobbyId),
                            HttpMethod.GET,
                            "application/x-www-form-urlencoded"
                        ) {
                            textField("get game")
                        }
                    }
                )
            } else ResponseEntity.status(404).body(
                siren(MessageOutputModel("WAITING")) {
                    clazz("Check if game is created!")
                    link(PathTemplate.home(), LinkRelations.HOME)
                    link(PathTemplate.isGameCreated(lobbyId), LinkRelations.SELF)
                }
            )
        } catch (ex: Exception) {
            ResponseEntity.status(404).body(
                siren(ErrorOutputModel(404, "Game has not been created!")) {}
            )
        }
    }

    @Authenticated
    @GetMapping(PathTemplate.GAME_BY_ID)
    fun getGameById(
        request: HttpServletRequest,
        @PathVariable("id") gameId: UUID
    ): ResponseEntity<SirenModel<OutputModel>> {
        request.getAttribute(AuthenticatedUserArgumentResolver.getKey()) as AuthenticatedUser?
            ?: return ResponseEntity.status(401).body(
                siren(ErrorOutputModel(401, "User not authenticated!")) {}
            )

        return try {
            val game = gomokuService.getById(gameId)
            val gameModel = GameOutputModel(
                id = game.gameId,
                userB = game.users.first,
                userW = game.users.second,
                turn = game.currentPlayer.first.username,
                rules = game.rules,
                boardCells = game.board.positions,
                boardState = game.board.typeToString()
            )
            ResponseEntity.status(200).body(

                siren(gameModel) {
                    clazz("Game")
                    action(
                        "play",
                        PathTemplate.play(game.gameId),
                        HttpMethod.POST,
                        "application/x-www-form-urlencoded"
                    ) {
                        textField("place piece")
                    }
                    link(PathTemplate.home(), LinkRelations.HOME)
                    link(PathTemplate.gameById(game.gameId), LinkRelations.GAME)
                }
            )
        } catch (ex: Exceptions.GameDoesNotExistException) {
            ResponseEntity.status(404).body(
                siren(ErrorOutputModel(404, ex.message)) {}
            )
        }
    }
}