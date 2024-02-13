package tech.filatov.auth.authentication

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import tech.filatov.auth.authentication.dto.AuthRequest
import tech.filatov.auth.authentication.dto.AuthResponse
import tech.filatov.auth.authentication.dto.OtpRequest
import tech.filatov.auth.security.JwtService
import tech.filatov.auth.user.UserService
import tech.filatov.auth.user.dto.UserRequest

@Service
class AuthenticationService (
    val oneTimePasswordService: OneTimePasswordService,
    val userService: UserService,
    val authenticationManager: AuthenticationManager,
    val jwtService: JwtService
) {

    fun login(authRequest: AuthRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.phoneNumber,
                authRequest.password
            )
        )
        oneTimePasswordService.send(authRequest.phoneNumber)
        return AuthResponse(jwtService.generateTemporaryToken())
    }

    fun register(userRequest: UserRequest): AuthResponse {
        userService.create(userRequest)
        oneTimePasswordService.send(userRequest.phoneNumber!!)
        return AuthResponse(jwtService.generateTemporaryToken())
    }

    fun verifyOtp(otpRequest: OtpRequest): AuthResponse {
        val user = userService.getUserByPhoneNumber(otpRequest.phoneNumber)
        oneTimePasswordService.verify(otpRequest.oneTimePassword)
        return AuthResponse(jwtService.generateToken(user))
    }
}
