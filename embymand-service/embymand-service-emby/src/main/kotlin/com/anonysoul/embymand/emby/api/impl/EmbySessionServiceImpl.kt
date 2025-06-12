package com.anonysoul.embymand.emby.api.impl

import com.anonysoul.embymand.emby.api.EmbySessionService
import embyclient.api.SessionsServiceApi
import embyclient.model.PlaystateCommand
import embyclient.model.PlaystateRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EmbySessionServiceImpl(
    val sessionsServiceApi: SessionsServiceApi,
) : EmbySessionService {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun stopPlaying(
        id: String,
        reason: String?,
    ) {
        val request =
            PlaystateRequest().apply {
                command = PlaystateCommand.STOP
            }
        sessionsServiceApi.postSessionsByIdPlayingByCommand(request, id, PlaystateCommand.STOP)
        if (reason != null) {
            sessionsServiceApi.postSessionsByIdMessage(id, "会话被终止：$reason", "警告", 10000)
        }
    }
}
