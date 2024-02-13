package tech.filatov.auth.authentication

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import tech.filatov.auth.authentication.dto.AuthRequest
import tech.filatov.auth.authentication.dto.AuthResponse
import tech.filatov.auth.authentication.dto.OtpRequest
import tech.filatov.auth.user.dto.UserRequest

@Controller
class AuthenticationController (var authenticationService: AuthenticationService) {

    @QueryMapping
    fun login(@Argument input: AuthRequest): AuthResponse = authenticationService.login(input)

    @MutationMapping
    fun register(@Argument input: UserRequest): AuthResponse = authenticationService.register(input)

    @Secured("ROLE_TEMP")
    @QueryMapping
    fun verifyOtp(@Argument input: OtpRequest): AuthResponse = authenticationService.verifyOtp(input)
}
