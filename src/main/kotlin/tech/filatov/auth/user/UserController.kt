package tech.filatov.auth.user

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import tech.filatov.auth.user.dto.UserRequest
import tech.filatov.auth.user.dto.UserResponse

@Controller
@Secured("ROLE_ADMIN")
class UserController(var service: UserService) {

    @QueryMapping
    fun userById(@Argument userId: String) : UserResponse = service.get(userId)

    @QueryMapping
    fun userByPhoneNumber(@Argument phoneNumber: Long) : UserResponse = service.getByPhoneNumber(phoneNumber)

    @MutationMapping
    fun createUser(@Argument input: UserRequest): UserResponse = service.create(input)

    @MutationMapping
    fun updateUser(@Argument input: UserRequest): UserResponse = service.update(input)

    @MutationMapping
    fun deleteUser(@Argument userId: String): Boolean = service.delete(userId)
}
