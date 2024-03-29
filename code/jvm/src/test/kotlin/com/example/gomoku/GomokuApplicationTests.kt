package com.example.gomoku

import com.example.gomoku.domain.*
import com.example.gomoku.repository.TransactionManager
import com.example.gomoku.repository.UsersRepository
import com.example.gomoku.service.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList
import java.net.http.HttpHeaders
import java.util.UUID


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GomokuApplicationTests {

    @LocalServerPort
    var port : Int = 8080

    val url = "http://localhost:$port"

    @Test
    fun getUserWebTestClient(){
        val client = WebTestClient.bindToServer().baseUrl(url).build()
        client.get().uri("/users/2e6df5e3-e568-4e22-9439-74e9d9ba5b40")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("username").isEqualTo("Thiago")
    }


    @Test
    fun addUserWebTestClient(){
        val client = WebTestClient.bindToServer().baseUrl(url).build()
        val user = """
            {
                "name": "john",
                "password": "admin"
            }
        """
        client.post().uri("/users/signup")
            .header("Content-Type","application/json")
            .bodyValue(user)
            .exchange()
            .expectStatus().isOk



    }

    @Test
    fun loginUserWebTestClient(){
        val user = """
            {
                "name":"john",
                "password":"admin"
            }
        """

        val client = WebTestClient.bindToServer().baseUrl(url).build()
        client.post().uri("/users/login")
            .header("Content-Type","application/json")
            .bodyValue(user)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("username").isEqualTo("john")
    }


}