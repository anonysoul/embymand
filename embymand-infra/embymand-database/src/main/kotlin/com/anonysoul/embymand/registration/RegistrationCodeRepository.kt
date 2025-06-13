package com.anonysoul.embymand.registration

import org.springframework.data.repository.Repository

interface RegistrationCodeRepository : Repository<RegistrationCode, Long> {
    fun save(registrationCode: RegistrationCode)
    fun saveAll(registrationCodes: Iterable<RegistrationCode>)
    fun findByCode(code: String): RegistrationCode?
}
