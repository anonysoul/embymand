package com.anonysoul.embymand.bot.consumer.checkin.event

import org.springframework.context.ApplicationEvent

class CheckInEvent(
    val chatId: Long,
    val callbackQueryId: String,
    val originMessageId: Int,
) : ApplicationEvent(chatId)
