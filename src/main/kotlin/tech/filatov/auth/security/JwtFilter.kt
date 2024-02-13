package tech.filatov.auth.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import tech.filatov.auth.user.Role

@Component
class JwtFilter(
    @Lazy var userDetailsService: UserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authHeader: String? = request.getHeader("Authorization")

        if (!authHeader.isNullOrBlank()) {
            if (authHeader.startsWith("temp_")) {
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                    "tempUser", null, mutableSetOf(Role.TEMP)
                )
            } else {
                val extractedPhoneNumberFromJwt = 7777777L
                val userDetails: UserDetails = userDetailsService.loadUserByUsername(extractedPhoneNumberFromJwt.toString())
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                    "mockedAuthorizedUser", null, userDetails.authorities
                )
            }
        }
        filterChain.doFilter(request, response)
    }
}