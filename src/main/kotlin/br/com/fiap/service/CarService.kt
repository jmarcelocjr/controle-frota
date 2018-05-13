package br.com.fiap.service

import br.com.fiap.entity.Car
import br.com.fiap.repository.CarRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CarService {
    @Autowired
    lateinit var carRepository: CarRepository

    fun findByStatus(status: String): List<Car>? {
        return carRepository.findByStatus(status)
    }

    fun register(car: Car) {
        carRepository.save(car)
    }
}