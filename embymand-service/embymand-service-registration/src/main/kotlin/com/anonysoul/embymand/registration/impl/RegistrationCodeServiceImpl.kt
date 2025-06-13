package com.anonysoul.embymand.registration.impl

import com.anonysoul.embymand.registration.RegistrationCode
import com.anonysoul.embymand.registration.RegistrationCodeRepository
import com.anonysoul.embymand.registration.RegistrationCodeService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RegistrationCodeServiceImpl(
    private val registrationCodeRepository: RegistrationCodeRepository
) : RegistrationCodeService {
    @Transactional
    override fun create(count: Int): List<String> {
        val list = mutableListOf<RegistrationCode>()
        while (list.size < count) {
            val code = UUID.randomUUID().toString().replace("-", "")
            list.add(RegistrationCode(code))
        }
        registrationCodeRepository.saveAll(list)
        return list.map { it.code }
    }
}