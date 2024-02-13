package tech.filatov.auth.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import tech.filatov.auth.common.exception.NotFoundException
import tech.filatov.auth.user.User
import tech.filatov.auth.user.UserRepository

@Configuration
@EnableMethodSecurity(securedEnabled = true)
class SecurityConfig (
    val userRepository: UserRepository,
    val jwtFilter: JwtFilter
    ) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke {
            cors { disable() }
            csrf { disable() }
            headers { frameOptions { disable() } }
            authorizeRequests {
                authorize("/h2-console/*", permitAll)
                authorize("/graphql", permitAll)
                authorize(anyRequest, denyAll)
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtFilter)
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS  }
        }
        return http.build()
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager

    @Bean
    fun authenticationProvider(): ProviderManager {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService())
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return ProviderManager(authenticationProvider)
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { phoneNumber: String? ->
            userRepository.findByPhoneNumber(phoneNumber!!.toLong()).orElseThrow {
                NotFoundException(User::class, "phoneNumber", phoneNumber)
            }
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
