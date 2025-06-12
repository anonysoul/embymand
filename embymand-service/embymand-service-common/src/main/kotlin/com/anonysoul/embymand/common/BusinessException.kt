package com.anonysoul.embymand.common

/**
 * 业务异常
 *
 * 此类异常是需要反馈给用户看到的异常
 */
class BusinessException(msg: String? = null, cause: Throwable? = null) : RuntimeException(msg, cause)
