import com.anonysoul.embymand.build.dependencie.Modules

plugins {
    id("embymand-base")
    id("embymand-codestyle")
}

dependencies {
    api(project(":embymand-service:embymand-service-common"))

    implementation(Modules.springBootStarter)
    implementation(Modules.embyclient)
}
