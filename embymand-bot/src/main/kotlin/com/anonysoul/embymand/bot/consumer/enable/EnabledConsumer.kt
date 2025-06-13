package com.anonysoul.embymand.bot.consumer.enable

import com.anonysoul.embymand.bot.consumer.BaseConsumer
import com.anonysoul.embymand.bot.consumer.enable.event.EnabledEvent
import com.anonysoul.embymand.user.RoleTO
import com.anonysoul.embymand.user.UserCreateInputTO
import com.anonysoul.embymand.user.UserService
import com.anonysoul.embymand.user.UserTO
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.BotCommand
import com.pengrad.telegrambot.model.botcommandscope.BotCommandsScopeChat
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.request.SendMessage
import com.pengrad.telegrambot.request.SendPhoto
import com.pengrad.telegrambot.request.SetMyCommands
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.telegram.bot.BotProperties

@Component
class EnabledConsumer(
    private val bot: TelegramBot,
    private val botProperties: BotProperties,
    private val userService: UserService,
    @Value("\${emby.title}")
    val embyServerTitle: String,
    @Value("\${emby.description}")
    val embyServerDescription: String,
    @Value("\${emby.urls}")
    val embyServerUrls: List<String>,
) : BaseConsumer(bot) {
    @EventListener
    fun onEvent(event: EnabledEvent) {
        var user = userService.findById(event.userId)
        if (user == null) {
            val messageId = sendInitializingMessage(event.userId)
            user = createUser(event.userId)
            deleteMessage(event.userId, messageId)
        }

        sendCommands(user)
        sendWelcomeMessage(user)
    }

    private fun sendInitializingMessage(userId: Long): Int {
        val request = SendMessage(userId, "正在初始化...")
        val response = bot.execute(request)
        return response.message().messageId()
    }

    /**
     * 系统管理员可使用的命令
     */
    val systemAdminCommands =
        arrayOf(
            BotCommand("start", "开始"),
            BotCommand("settings", "设置"),
        )

    /**
     * 普通管理员可使用的命令
     */
    val adminCommands =
        arrayOf(
            BotCommand("start", "开始"),
        )

    /**
     * 普通用户可使用的命令
     */
    val userCommands =
        arrayOf(
            BotCommand("start", "开始"),
            BotCommand("check_in", "签到"),
        )

    /**
     * 设置 bot 命令
     */
    private fun sendCommands(user: UserTO) {
        val command =
            when (user.role) {
                RoleTO.SYSTEM_ADMIN -> systemAdminCommands
                RoleTO.ADMIN -> adminCommands
                RoleTO.USER -> userCommands
            }
        val setMyCommandsRequest =
            SetMyCommands(*command).scope(
                BotCommandsScopeChat(user.id),
            )
        bot.execute(setMyCommandsRequest)
    }

    private fun createUser(userId: Long): UserTO {
        val role =
            if (botProperties.owner == userId) {
                RoleTO.SYSTEM_ADMIN
            } else {
                RoleTO.USER
            }
        val input = UserCreateInputTO(userId, role)
        return userService.createUser(input)
    }

    private fun sendWelcomeMessage(user: UserTO) {
        val logo = ClassPathResource("images/emby.png").file
        val text = generateWelcomeMessageContent()
        val request =
            SendPhoto(user.id, logo).apply {
                caption(text)
                parseMode(ParseMode.MarkdownV2)
            }
        bot.execute(request)
    }

    private fun generateWelcomeMessageContent(): String {
        return if (embyServerUrls.size == 1) {
            """
            ***$embyServerTitle***
            >$embyServerDescription
            
            ***服务器地址：***`${embyServerUrls[0]}`
            """.trimIndent()
        } else {
            val sb = StringBuilder()
            sb.append("***$embyServerTitle***\n")
            sb.append(">$embyServerDescription\n\n")
            sb.append("***服务器地址：***\n")
            embyServerUrls.forEach {
                sb.append("● `$it`\n")
            }
            sb.toString()
        }
    }
}
