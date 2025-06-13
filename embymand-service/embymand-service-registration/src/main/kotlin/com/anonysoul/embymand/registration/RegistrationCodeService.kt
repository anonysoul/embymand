package com.anonysoul.embymand.registration

interface RegistrationCodeService {
    fun create(count: Int): List<String>
}