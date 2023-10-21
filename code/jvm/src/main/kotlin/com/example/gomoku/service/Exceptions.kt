package com.example.gomoku.service


class Exceptions : Exception(){
    class UsernameAlreadyInUseException(message: String) : Exception(message)

    class WrongUserOrPasswordException(message: String) : Exception(message)

    class NotFoundException() : Exception()
    class GameDoesNotExistException(message: String) : Exception(message)
    class ErrorCreatingUserException(message: String) : Exception(message)
    class PlayNotAllowedException(message: String) : Exception(message)
    class WrongPlayException(message: String) : Exception(message)
}


