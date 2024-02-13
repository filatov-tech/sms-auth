package tech.filatov.auth.user.dto

data class UserRequest(
    val id: String?,
    val firstname: String?,
    val lastname: String?,
    val phoneNumber: Long?,
    val password: String?
)
