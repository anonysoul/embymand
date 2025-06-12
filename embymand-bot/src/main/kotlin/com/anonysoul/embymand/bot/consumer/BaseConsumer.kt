package com.anonysoul.embymand.bot.consumer

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.request.ChatAction
import com.pengrad.telegrambot.model.request.ReplyParameters
import com.pengrad.telegrambot.request.AnswerCallbackQuery
import com.pengrad.telegrambot.request.DeleteMessage
import com.pengrad.telegrambot.request.SendChatAction
import com.pengrad.telegrambot.request.SendMessage
import org.slf4j.LoggerFactory

abstract class BaseConsumer(
    private val bot: TelegramBot,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun deleteMessage(chatId: Long, messageId: Int) {
        val request = DeleteMessage(chatId, messageId)
        bot.execute(request)
    }

    fun setTyping(chatId: Long) {
        val sendChatAction = SendChatAction(chatId, ChatAction.typing)
        val response = bot.execute(sendChatAction)
        if (!response.isOk) {
            logger.warn("Failed to send message: ${response.description()}")
        }
    }

    fun sendTextMessage(
        chatId: Long,
        text: String,
        replyToMessageId: Int? = null,
    ) {
        val request =
            SendMessage(chatId, text).apply {
                replyToMessageId?.let {
                    val params = ReplyParameters(it)
                    replyParameters(params)
                }
            }
        val response = bot.execute(request)
        if (!response.isOk) {
            logger.warn("Failed to send message: ${response.description()}")
        }
    }

    fun sendCallbackQueryAnswer(
        callbackQueryId: String,
        text: String,
        showAlert: Boolean,
    ) {
        val answer =
            AnswerCallbackQuery(callbackQueryId).apply {
                text(text)
                showAlert(showAlert)
            }
        val response = bot.execute(answer)
        if (!response.isOk) {
            logger.warn("Failed to send message: ${response.description()}")
        }
    }
}
