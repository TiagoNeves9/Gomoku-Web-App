package com.example.gomoku.service


class Exceptions : Exception(){
    class UsernameAlreadyInUseException(message: String) : Exception(message)

    class WrongUserOrPasswordException(message: String) : Exception(message)

    class ErrorUpdatingUserScore(message: String) : Exception(message)

    class NotFound(message: String) : Exception(message)

    class MatchmakingErrorException(message: String) : Exception(message)
    class DeleteNotSuccessful(message: String) : Exception(message)
    class ErrorCreatingGameException(message: String) : Exception(message)
    class ErrorCreatingLobbyException(message: String) : Exception(message) {

    }
}


