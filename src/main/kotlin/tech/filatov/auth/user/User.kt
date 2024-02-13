package tech.filatov.auth.user

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "USERS")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String?,
    @get:JvmName("getPasswordProperty")
    var password: String,
    var firstname: String,
    var lastname: String,
    var phoneNumber: Long,
    @Enumerated(EnumType.STRING)
    var role: Role
)  : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableSetOf(role)

    override fun getPassword(): String = password

    override fun getUsername(): String = "$firstname $lastname"

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

}
