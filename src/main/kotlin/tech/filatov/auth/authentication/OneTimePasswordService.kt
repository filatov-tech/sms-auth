package tech.filatov.auth.authentication

import org.springframework.stereotype.Service

@Service
class OneTimePasswordService {
    fun send(phoneNumber: Long): Int = 111111
    fun verify(oneTimePassword: Int) {
        if (oneTimePassword != 111111) throw IllegalArgumentException("The one-time password is incorrect")
    }
}
