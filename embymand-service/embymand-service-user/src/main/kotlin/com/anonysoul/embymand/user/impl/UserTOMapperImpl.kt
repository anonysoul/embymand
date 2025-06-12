package com.anonysoul.embymand.user.impl

import com.anonysoul.embymand.user.Role
import com.anonysoul.embymand.user.RoleTO
import com.anonysoul.embymand.user.UserTO
import com.anonysoul.embymand.user.User
import org.springframework.stereotype.Component

@Component
internal class UserTOMapperImpl : UserTOMapper {
    override fun map(user: User): UserTO {
        return UserTO(
            id = user.id,
            enabled = user.enabled,
            locked = user.locked,
            role = map(user.role),
            createdAt = user.createdAt.epochSecond,
        )
    }

    override fun map(role: RoleTO): Role {
        return when (role) {
            RoleTO.USER -> Role.USER
            RoleTO.ADMIN -> Role.ADMIN
            RoleTO.SYSTEM_ADMIN -> Role.SYSTEM_ADMIN
        }
    }

    override fun map(role: Role): RoleTO {
        return when (role) {
            Role.USER -> RoleTO.USER
            Role.ADMIN -> RoleTO.ADMIN
            Role.SYSTEM_ADMIN -> RoleTO.SYSTEM_ADMIN
        }
    }
}
