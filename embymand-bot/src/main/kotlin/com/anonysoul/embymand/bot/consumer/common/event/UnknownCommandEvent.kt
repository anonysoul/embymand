package com.anonysoul.embymand.bot.consumer.common.event

import org.springframework.context.ApplicationEvent

/**
 * 未知命令事件
 */
class UnknownCommandEvent(
    val chatId: Long,
    val messageId: Int,
) : ApplicationEvent(chatId)
