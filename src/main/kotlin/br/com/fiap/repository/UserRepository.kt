package br.com.fiap.repository

import br.com.fiap.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {
    fun findByLoginAndPassword(login: String, password: String): User?
}