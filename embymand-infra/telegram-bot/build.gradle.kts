import com.anonysoul.embymand.build.dependencie.Modules

plugins {
    id("embymand-base")
    id("embymand-codestyle")
}

dependencies {
    implementation(Modules.springBootStarter)
    api(Modules.telegramBotApi)
}
