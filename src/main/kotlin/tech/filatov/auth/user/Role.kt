package tech.filatov.auth.user

import org.springframework.security.core.GrantedAuthority

enum class Role : GrantedAuthority {
    TEMP, USER, ADMIN;

    override fun getAuthority(): String = "ROLE_$name"
}
