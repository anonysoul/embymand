package com.anonysoul.embymand.bot.consumer.common

import com.anonysoul.embymand.bot.consumer.BaseConsumer
import com.anonysoul.embymand.bot.consumer.common.event.InsufficientPermissionEvent
import com.anonysoul.embymand.bot.consumer.common.event.UnknownCallbackQueryEvent
import com.anonysoul.embymand.bot.consumer.common.event.UnknownCommandEvent
import com.pengrad.telegrambot.TelegramBot
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CommonConsumer(
    private val bot: TelegramBot,
) : BaseConsumer(bot) {
    @EventListener
    fun onEvent(event: UnknownCommandEvent) {
        sendTextMessage(event.chatId, "未知的命令", event.messageId)
    }

    @EventListener
    fun onEvent(event: UnknownCallbackQueryEvent) {
        sendCallbackQueryAnswer(event.callbackQueryId, "操作失败，请尝试重新获取消息", false)
    }

    @EventListener
    fun onEvent(event: InsufficientPermissionEvent) {
        if (event.messageId != null) {
            sendTextMessage(event.chatId, "权限不足", event.messageId)
        } else if (event.callbackQueryId != null) {
            sendCallbackQueryAnswer(event.callbackQueryId, "权限不足", false)
        }
    }
}
