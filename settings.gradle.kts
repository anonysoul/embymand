rootProject.name = "embymand"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

include("embymand-bot")

include("embymand-core:embymand-integral")
include("embymand-core:embymand-user")
include("embymand-core:embymand-registration")

include("embymand-infra:embymand-database")
include("embymand-infra:telegram-bot")

include("embymand-service:embymand-service-common")
include("embymand-service:embymand-service-emby")
include("embymand-service:embymand-service-integral")
include("embymand-service:embymand-service-registration")
include("embymand-service:embymand-service-user")
