package com.anonysoul.embymand.user

data class UserTO(
    val id: Long,
    val enabled: Boolean,
    val locked: Boolean,
    val role: RoleTO,
    val createdAt: Long,
)
