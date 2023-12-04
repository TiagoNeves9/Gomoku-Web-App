package com.example.gomoku

import com.example.gomoku.http.pipeline.AuthInterceptor
import com.example.gomoku.http.pipeline.AuthenticatedUserArgumentResolver
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


const val SYSTEM_VERSION = "1.0.0"

@SpringBootApplication
class GomokuApplication {
    @Bean
    fun jdbi(): Jdbi {
        val jdbcDatabaseURL =
            System.getenv("JDBC_DATABASE_URL")
                ?: "jdbc:postgresql://localhost/postgres?user=postgres&password=admin"
        val dataSource = PGSimpleDataSource()
        dataSource.setURL(jdbcDatabaseURL)

        return Jdbi.create(dataSource).installPlugin(KotlinPlugin())
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}

@Configuration
class PipelineConfigurator(
    val authenticationInterceptor: AuthInterceptor,
    val authenticatedUserArgumentResolver: AuthenticatedUserArgumentResolver
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticationInterceptor)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authenticatedUserArgumentResolver)
    }
}


fun main(args: Array<String>) {
    runApplication<GomokuApplication>(*args)
}