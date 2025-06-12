package com.anonysoul.embymand.user

import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface UserService {
    fun findById(id: Long): UserTO?

    fun existsById(id: Long): Boolean

    fun createUser(
        @Valid userCreateInputTO: UserCreateInputTO,
    ): UserTO

    fun updateUser(
        id: Long,
        @Valid userUpdateInputTO: UserUpdateInputTO,
    ): UserTO

    fun deleteUser(id: Long)

    fun enableUser(id: Long)

    fun disableUser(id: Long)

    fun lockUser(id: Long)

    fun unlockUser(id: Long)
}
