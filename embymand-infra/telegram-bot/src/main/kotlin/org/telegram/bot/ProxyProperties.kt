package org.telegram.bot

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.net.Proxy
import kotlin.properties.Delegates

@Configuration
@ConfigurationProperties(prefix = "proxy")
class ProxyProperties {
    var enabled by Delegates.notNull<Boolean>()
    var type by Delegates.notNull<Proxy.Type>()
    var host by Delegates.notNull<String>()
    var port by Delegates.notNull<Int>()
}
