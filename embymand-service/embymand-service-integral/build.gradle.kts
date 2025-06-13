import com.anonysoul.embymand.build.dependencie.Modules

plugins {
    id("embymand-base")
    id("embymand-codestyle")
}

dependencies {
    implementation(project(":embymand-core:embymand-integral"))
    implementation(project(":embymand-infra:embymand-database"))
    api(project(":embymand-service:embymand-service-common"))

    implementation(Modules.springBootAutoConfigure)
    implementation(Modules.jakartaPersistence)
    implementation(Modules.jakartaTransaction)
    implementation(Modules.jakartaValidation)
    implementation(Modules.hibernateCore)
}
