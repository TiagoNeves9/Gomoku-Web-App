package com.example.gomoku.http.pipeline


// This annotation is used to mark a route as requiring authentication.
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Authenticated
