package tech.filatov.auth.user.dto

import tech.filatov.auth.user.User

data class UserResponse(
    val id: String?,
    val firstname: String,
    val lastname: String,
    val phoneNumber: Long
) {
    companion object {
        fun from(user: User): UserResponse = UserResponse(
            id = user.id,
            firstname = user.firstname,
            lastname = user.lastname,
            phoneNumber = user.phoneNumber
        )
    }
}
