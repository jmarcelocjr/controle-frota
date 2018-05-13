package br.com.fiap.service

import br.com.fiap.entity.User
import br.com.fiap.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    fun findByLoginAndPassword(login: String, password: String): User? {
        return userRepository.findByLoginAndPassword(login, password)
    }

    fun register(user:User) {
        userRepository.save(user)
    }
}