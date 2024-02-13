package tech.filatov.auth.user

import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tech.filatov.auth.common.exception.NotFoundException
import tech.filatov.auth.user.dto.UserRequest
import tech.filatov.auth.user.dto.UserResponse

@Service
class UserService(
    var repository: UserRepository,
    var passwordEncoder: PasswordEncoder
) {

    fun get(userId: String) : UserResponse {
        val user = repository.findById(userId)
            .orElseThrow { NotFoundException(User::class, "userId", userId) }
        return UserResponse.from(user)
    }

    fun getByPhoneNumber(phoneNumber: Long): UserResponse {
        val user = repository.findByPhoneNumber(phoneNumber)
            .orElseThrow { NotFoundException(User::class, "phoneNumber", phoneNumber.toString()) }
        return UserResponse.from(user)
    }

    fun getUserByPhoneNumber(phoneNumber: Long): User {
        return repository.findByPhoneNumber(phoneNumber)
            .orElseThrow { NotFoundException(User::class, "phoneNumber", phoneNumber.toString()) }
    }

    @Transactional
    fun create(userRequest: UserRequest): UserResponse {
        val user = User(
            userRequest.id,
            passwordEncoder.encode(userRequest.password!!),
            userRequest.firstname!!,
            userRequest.lastname!!,
            userRequest.phoneNumber!!,
            Role.ADMIN
        )
        return UserResponse.from(repository.save(user))
    }

    @Transactional
    fun update(userRequest: UserRequest): UserResponse {
        val id = userRequest.id ?: throw IllegalArgumentException("User update ID missing")
        val user = repository.findById(id).orElseThrow { NotFoundException(User::class, "userId", id) }

        user.password = passwordEncoder.encode(user.password) ?: user.password
        user.firstname = userRequest.firstname ?: user.firstname
        user.lastname = userRequest.lastname ?: user.lastname
        user.phoneNumber = userRequest.phoneNumber ?: user.phoneNumber

        return UserResponse.from(repository.save(user))
    }

    @Transactional
    fun delete(userId: String): Boolean {
        get(userId)
        repository.deleteById(userId)
        return true
    }
}