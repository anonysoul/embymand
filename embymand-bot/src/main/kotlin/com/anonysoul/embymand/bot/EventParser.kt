package com.anonysoul.embymand.bot

import com.anonysoul.embymand.bot.consumer.checkin.event.CheckInEvent
import com.anonysoul.embymand.bot.consumer.common.event.UnknownCallbackQueryEvent
import com.anonysoul.embymand.bot.consumer.common.event.UnknownCommandEvent
import com.anonysoul.embymand.bot.consumer.enable.event.EnabledEvent
import com.anonysoul.embymand.bot.consumer.registration.event.GenerateRegistrationCodeRequestEvent
import com.pengrad.telegrambot.model.Chat
import com.pengrad.telegrambot.model.Update
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEvent
import org.springframework.stereotype.Component
import org.telegram.bot.BotProperties

/**
 * 事件解析器
 *
 * 解析从 telegram bot api 获取到的事件
 */
@Component
class EventParser(
    private val botProperties: BotProperties,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun parse(update: Update): ApplicationEvent? {
        return try {
            if (update.message() != null && update.message().text() != null) {
                if (update.message().chat().type() == Chat.Type.Private) {
                    if (update.message().text().startsWith("/")) {
                        parsePrivateCommandMessage(update)
                    } else {
                        parsePrivateTextMessage(update)
                    }
                } else if (
                    update.message().chat().type() == Chat.Type.group ||
                    update.message().chat().type() == Chat.Type.supergroup
                ) {
                    if (update.message().text().startsWith("/start@${botProperties.username} ")) {
                        parseGroupCommandMessage(update)
                    } else {
                        parseGroupTextMessage(update)
                    }
                } else {
                    logger.warn("未能正确解析事件: $update")
                    null
                }
            } else if (update.callbackQuery() != null) {
                parseCallbackQuery(update)
            } else {
                if (
                    update.myChatMember()?.chat()?.type() == Chat.Type.group ||
                    update.myChatMember()?.chat()?.type() == Chat.Type.supergroup ||
                    update.message()?.chat()?.type() == Chat.Type.group ||
                    update.message()?.chat()?.type() == Chat.Type.supergroup
                ) {
                    parseGroupOtherMessage(update)
                } else {
                    logger.warn("未能正确解析事件: $update")
                    null
                }
            }
        } catch (e: Exception) {
            logger.error("解析事件失败", e)
            null
        }
    }

    /**
     * 私聊 - 命令消息
     *
     * update.message() != null
     * update.message().text() != null
     * update.message().text().startsWith("/")
     * update.message().chat().type() == Chat.Type.Private
     */
    fun parsePrivateCommandMessage(update: Update): ApplicationEvent? {
        return when {
            update.message().text() == "/start" ->
                EnabledEvent(
                    update.message().from().id(),
                    update.message().from().languageCode(),
                )
            update.message().text() == "/generate_registration_code" ->
                GenerateRegistrationCodeRequestEvent(
                    update.message().from().id(),
                )

            else ->
                UnknownCommandEvent(
                    update.message().from().id(),
                    update.message().messageId(),
                )
        }
    }

    /**
     * 私聊 - 纯文本消息
     *
     * update.message() != null
     * update.message().text() != null
     * update.message().text().startsWith("/").not()
     * update.message().chat().type() == Chat.Type.Private
     */
    fun parsePrivateTextMessage(update: Update): ApplicationEvent? {
        return null
    }

    private val checkInCallBackQueryRegex = "^check_in$".toRegex()

    /**
     * 键盘消息
     *
     * update.callbackQuery() != null
     */
    fun parseCallbackQuery(update: Update): ApplicationEvent? {
        val callbackData = update.callbackQuery().data()
        return when {
            checkInCallBackQueryRegex.matches(callbackData) -> {
                val chatId = update.callbackQuery().message().chat().id()
                val callbackQueryId = update.callbackQuery().id()
                val originMessageId = update.callbackQuery().message().messageId()
                CheckInEvent(
                    chatId,
                    callbackQueryId,
                    originMessageId,
                )
            }

            else ->
                UnknownCallbackQueryEvent(
                    update.message().from().id(),
                    update.callbackQuery().id(),
                )
        }
    }

    /**
     * 群组 - 命令消息
     *
     * update.message().chat().type() == Chat.Type.group || update.message().chat().type() == Chat.Type.supergroup
     * update.message().text().startsWith("/start@${botProperties.username} ")
     */
    fun parseGroupCommandMessage(update: Update): ApplicationEvent? {
        return null
    }

    /**
     * 群组 - 纯文本消息
     *
     * update.message().chat().type() == Chat.Type.group || update.message().chat().type() == Chat.Type.supergroup
     * update.message().text() != null
     */
    fun parseGroupTextMessage(update: Update): ApplicationEvent? {
        return null
    }

    /**
     * 群组 - 其他消息
     */
    fun parseGroupOtherMessage(update: Update): ApplicationEvent? {
        return null
    }
}
