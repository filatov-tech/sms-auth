package tech.filatov.auth.authentication.dto

data class OtpRequest(
    val oneTimePassword: Int,
    val phoneNumber: Long
)
