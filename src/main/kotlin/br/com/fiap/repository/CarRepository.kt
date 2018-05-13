package br.com.fiap.repository

import br.com.fiap.entity.Car
import org.springframework.data.mongodb.repository.MongoRepository

interface CarRepository : MongoRepository<Car, String> {
    fun findByStatus(status: String): List<Car>?
}