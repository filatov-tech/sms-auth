package tech.filatov.auth.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class JwtService {
    fun generateToken(userDetails: UserDetails) = "mockedJwtToken"
    fun generateTemporaryToken() = "temp_mockedJwtToken"
}