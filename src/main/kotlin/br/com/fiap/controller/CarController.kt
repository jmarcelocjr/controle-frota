package br.com.fiap.controller

import br.com.fiap.entity.Car
import br.com.fiap.service.CarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/car")
class CarController {
    @Autowired
    lateinit var carService: CarService

    @PostMapping
    fun register(@RequestBody car: Car) {
        carService.register(car)
    }
}