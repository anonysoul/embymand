package com.anonysoul.embymand.bot.consumer

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.request.ChatAction
import com.pengrad.telegrambot.model.request.ReplyParameters
import com.pengrad.telegrambot.request.AnswerCallbackQuery
import com.pengrad.telegrambot.request.DeleteMessage
import com.pengrad.telegrambot.request.SendChatAction
import com.pengrad.telegrambot.request.SendMessage
import org.slf4j.LoggerFactory
import kotlin.concurrent.thread

/**
 * 所有 consumer 的基类
 *
 * 此类中定义了所有 consumer 公用的方法
 */
abstract class BaseConsumer(
    private val bot: TelegramBot,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * 删除消息
     */
    protected fun deleteMessage(
        chatId: Long,
        messageId: Int,
    ) {
        val request = DeleteMessage(chatId, messageId)
        bot.execute(request)
    }

    /**
     * 发送正在输入的提示
     *
     * 只有需要用户等待的时候才需要发送，否则会拖慢程序的响应速度
     */
    protected fun setTyping(chatId: Long) {
        val sendChatAction = SendChatAction(chatId, ChatAction.typing)
        val response = bot.execute(sendChatAction)
        if (!response.isOk) {
            logger.warn("Failed to send message: ${response.description()}")
        }
    }

    /**
     * 发送文本消息
     *
     * @param chatId 聊天 id
     * @param text 消息内容
     * @param replyToMessageId 回复消息的 id
     */
    protected fun sendTextMessage(
        chatId: Long,
        text: String,
        replyToMessageId: Int? = null,
    ): Int {
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
        return response.message().messageId()
    }

    /**
     * 发送自动删除的文本消息
     *
     * @param chatId 聊天 id
     * @param text 消息内容
     * @param replyToMessageId 回复消息的 id
     * @param deleteAfter 自动删除的秒数
     */
    protected fun sendAutoDeleteTextMessage(
        chatId: Long,
        text: String,
        replyToMessageId: Int? = null,
        deleteAfter: Int = 5,
    ) {
        val messageId = sendTextMessage(chatId, text, replyToMessageId)
        thread {
            Thread.sleep(deleteAfter * 1000L)
            val deleteMessageRequest = DeleteMessage(chatId, messageId)
            bot.execute(deleteMessageRequest)
        }
    }

    protected fun sendCallbackQueryAnswer(
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
