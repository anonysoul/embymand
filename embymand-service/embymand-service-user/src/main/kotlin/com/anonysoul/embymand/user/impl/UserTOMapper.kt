package com.anonysoul.embymand.user.impl

import com.anonysoul.embymand.user.Role
import com.anonysoul.embymand.user.RoleTO
import com.anonysoul.embymand.user.User
import com.anonysoul.embymand.user.UserTO

internal interface UserTOMapper {
    fun map(user: User): UserTO

    fun map(role: RoleTO): Role

    fun map(role: Role): RoleTO
}
