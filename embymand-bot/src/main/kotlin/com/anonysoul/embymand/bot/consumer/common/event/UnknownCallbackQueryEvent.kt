package com.anonysoul.embymand.bot.consumer.common.event

import org.springframework.context.ApplicationEvent

/**
 * 未知键盘事件
 */
class UnknownCallbackQueryEvent(
    val chatId: Long,
    val callbackQueryId: String,
) : ApplicationEvent(chatId)
