package tech.filatov.auth.authentication.dto

data class AuthRequest(
    val phoneNumber: Long,
    val password: String,
)
