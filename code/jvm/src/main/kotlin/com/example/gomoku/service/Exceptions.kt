package com.example.gomoku.service


class Exceptions : Exception(){
    class UsernameAlreadyInUseException(message: String) : Exception(message)

    class WrongUserOrPasswordException(message: String) : Exception(message)

    class NotFound() : Exception()
    class GameDoesNotExistException(message: String) : Exception(message)
    class ErrorCreatingUser(message: String) : Exception(message)
}


