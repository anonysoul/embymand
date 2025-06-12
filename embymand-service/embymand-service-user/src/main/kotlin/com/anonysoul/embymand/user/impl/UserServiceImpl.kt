package com.anonysoul.embymand.user.impl

import com.anonysoul.embymand.user.User
import com.anonysoul.embymand.user.UserCreateInputTO
import com.anonysoul.embymand.user.UserRepository
import com.anonysoul.embymand.user.UserService
import com.anonysoul.embymand.user.UserTO
import com.anonysoul.embymand.user.UserUpdateInputTO
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
internal class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userTOMapper: UserTOMapper,
) : UserService {
    @Transactional
    override fun findById(id: Long): UserTO? {
        return userRepository.findById(id)?.let {
            userTOMapper.map(it)
        }
    }

    @Transactional
    override fun existsById(id: Long): Boolean {
        return userRepository.existsById(id)
    }

    @Transactional
    override fun createUser(userCreateInputTO: UserCreateInputTO): UserTO {
        val role = userTOMapper.map(userCreateInputTO.role)
        val user = User(userCreateInputTO.id)
        user.changeRole(role)
        userRepository.save(user)
        return userTOMapper.map(user)
    }

    override fun updateUser(id: Long, userUpdateInputTO: UserUpdateInputTO): UserTO {
        val user = userRepository.findById(id) ?: throw RuntimeException("用户不存在")
        val role = userTOMapper.map(userUpdateInputTO.role)
        user.changeRole(role)
        userRepository.save(user)
        return userTOMapper.map(user)
    }

    @Transactional
    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    @Transactional
    override fun enableUser(id: Long) {
        val user = userRepository.findById(id) ?: throw RuntimeException("用户不存在")
        user.enable()
        userRepository.save(user)
    }

    @Transactional
    override fun disableUser(id: Long) {
        val user = userRepository.findById(id) ?: throw RuntimeException("用户不存在")
        user.disable()
        userRepository.save(user)
    }

    @Transactional
    override fun lockUser(id: Long) {
        val user = userRepository.findById(id) ?: throw RuntimeException("用户不存在")
        user.lock()
        userRepository.save(user)
    }

    @Transactional
    override fun unlockUser(id: Long) {
        val user = userRepository.findById(id) ?: throw RuntimeException("用户不存在")
        user.unlock()
        userRepository.save(user)
    }
}
