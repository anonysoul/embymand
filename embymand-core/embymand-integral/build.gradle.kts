import com.anonysoul.embymand.build.dependencie.Modules

plugins {
    id("embymand-base")
    id("embymand-codestyle")
    id("embymand-jpa")
}

dependencies {
    implementation(Modules.jakartaValidation)
}
