package com.anonysoul.embymand.bot.consumer.common.event

import org.springframework.context.ApplicationEvent

/**
 * 权限不足事件
 */
class InsufficientPermissionEvent(
    val chatId: Long,
    val messageId: Int?,
    val callbackQueryId: String?,
) : ApplicationEvent(chatId)
