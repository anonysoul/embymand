package com.anonysoul.embymand.bot.consumer.registration.event

import org.springframework.context.ApplicationEvent

class GenerateRegistrationCodeRequestEvent(
    val userId: Long,
) : ApplicationEvent(userId)
