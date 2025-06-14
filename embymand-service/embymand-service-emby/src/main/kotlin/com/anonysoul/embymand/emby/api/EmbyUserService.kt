package com.anonysoul.embymand.emby.api

interface EmbyUserService {
    /**
     * 创建用户
     */
    fun createUser(
        username: String,
        password: String?,
    )

    /**
     * 更新用户密码
     */
    fun updateUserPassword(
        userId: String,
        newPassword: String,
    )

    /**
     * 删除用户
     */
    fun deleteUser(userId: String)

    /**
     * 获取用户登录过的客户端信息
     */
    fun listClientByUser(userId: String): List<EmbyClientInfoTO>
}
