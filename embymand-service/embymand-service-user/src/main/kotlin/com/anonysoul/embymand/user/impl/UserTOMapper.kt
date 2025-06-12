package com.anonysoul.embymand.user.impl

import com.anonysoul.embymand.user.Role
import com.anonysoul.embymand.user.RoleTO
import com.anonysoul.embymand.user.UserTO
import com.anonysoul.embymand.user.User

internal interface UserTOMapper {
    fun map(user: User): UserTO
    fun map(role: RoleTO): Role
    fun map(role: Role): RoleTO
}
