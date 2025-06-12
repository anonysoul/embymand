package com.anonysoul.embymand.emby.api

interface EmbySessionService {
    fun stopPlaying(
        id: String,
        reason: String? = null,
    )
}
