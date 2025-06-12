package com.anonysoul.embymand.bot.consumer.enable.event

import org.springframework.context.ApplicationEvent

class EnabledEvent(
    val userId: Long,
    val languageCode: String,
) : ApplicationEvent(userId)
