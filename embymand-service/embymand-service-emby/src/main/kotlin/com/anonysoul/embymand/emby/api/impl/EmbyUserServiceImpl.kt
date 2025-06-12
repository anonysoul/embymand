package com.anonysoul.embymand.emby.api.impl

import com.anonysoul.embymand.common.BusinessException
import com.anonysoul.embymand.emby.api.EmbyBaseService
import com.anonysoul.embymand.emby.api.EmbyUserService
import embyclient.ApiException
import embyclient.api.UserServiceApi
import embyclient.model.CreateUserByName
import embyclient.model.UpdateUserPassword
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import kotlin.concurrent.thread

@Service
class EmbyUserServiceImpl(
    val userServiceApi: UserServiceApi,
    @Value("\${emby.user.default-password-length:6}")
    val defaultPasswordLength: Int,
) : EmbyUserService, EmbyBaseService() {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val usernameExistsRegex = """^A user with the name .* already exists\.$""".toRegex()

    override fun createUser(
        username: String,
        password: String?,
    ) {
        try {
            val createUserRequest =
                CreateUserByName().apply {
                    name = username
                }
            val user = userServiceApi.postUsersNew(createUserRequest)
            val newPassword = password ?: randomString(defaultPasswordLength)
            updateUserPassword(user.id, newPassword)
            logger.info("成功创建 emby 账户 [id: ${user.id} user: ${user.name} password: $password]")
        } catch (e: ApiException) {
            if (e.responseBody != null) {
                val usernameExists = usernameExistsRegex.matches(e.responseBody)
                if (usernameExists) {
                    throw BusinessException("此用户名已存在")
                }
            }
            logger.error("创建用户失败：${e.responseBody}")
        }
    }

    override fun updateUserPassword(
        userId: String,
        newPassword: String,
    ) {
        val updateUserPasswordRequest =
            UpdateUserPassword().apply {
                id = userId
                newPw = newPassword
            }
        userServiceApi.postUsersByIdPassword(updateUserPasswordRequest, userId)
    }

    override fun deleteUser(userId: String) {
        userServiceApi.deleteUsersById(userId)
    }

    init {
        thread {
            while (true) {
                Thread.sleep(3000)
                try {
//                    createUser("zhangwu", "123456")
                    var userId = "f34c6ea3a9a84a4eb0a61d34a0da2ea4"
                    xxxxx(userId)
                } catch (e: Exception) {
                    logger.info("")
                }
            }
        }
    }

    fun xxxxx(userId: String) {
        val sql = "SELECT DeviceName,ClientName, RemoteAddress FROM PlaybackActivity WHERE UserId = '$userId'"
        customQuery(sql, true)
    }

    private val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    /**
     * 生成随机字符串
     */
    private fun randomString(length: Int): String {
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
