package tech.filatov.auth.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun findByPhoneNumber(phoneNumber: Long): Optional<User>
}
