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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.Instant
import java.util.UUID

@SpringBootTest
class GomokuApplicationTests {

	@Mock
	private lateinit var transactionManager: TransactionManager

	@Mock
	private lateinit var usersRepository: UsersRepository

	private lateinit var userService: UserService

	@BeforeEach
	fun setup() {
		MockitoAnnotations.openMocks(this)
		userService = UserService(transactionManager, BCryptPasswordEncoder())
	}

	@Test
	fun createUser(){

		val username = "thiagolk"
		val password = "forte8password"
		val encoded = BCryptPasswordEncoder().encode(password)

		`when`(transactionManager.run { usersRepository.storeUser(username, encoded) }).thenReturn(1)
		`when`(transactionManager.run { usersRepository.getUserWithUsername(username) }).thenReturn(User(UUID.randomUUID(), username, encoded))

		// Teste da função createNewUser
		val createdUser = userService.createNewUser(username, password)

		// Verificação
		assert(createdUser.username == username)
		assert(createdUser.encodedPassword == encoded)


	}


}
