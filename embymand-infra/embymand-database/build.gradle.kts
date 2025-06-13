import com.anonysoul.embymand.build.dependencie.Modules

plugins {
    id("embymand-base")
    id("embymand-codestyle")
    id("embymand-jpa")
}

dependencies {
    implementation(project(":embymand-core:embymand-integral"))
    implementation(project(":embymand-core:embymand-user"))
    implementation(Modules.springBootAutoConfigure)
    implementation(Modules.jacksonDatabind)
    implementation(Modules.jakartaValidation)
    api(Modules.springDataJpa)
}
