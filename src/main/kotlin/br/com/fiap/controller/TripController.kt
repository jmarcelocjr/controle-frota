package br.com.fiap.controller

import br.com.fiap.entity.Car
import br.com.fiap.entity.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/trip")
class TripController {

    @GetMapping("/cost")
    fun cost(
        @RequestParam("fromLatitude") fromLatitude: Double,
        @RequestParam("fromLongitude") fromLongitude: Double,
        @RequestParam("toLatitude") toLatitude: Double,
        @RequestParam("toLongitude") toLongitude: Double
    ) {
        val valueKilometer = 2.00
        val valueMinute = 1.00
    }

    @PostMapping("/request")
    fun request(@RequestBody user: User) {

    }

    @PostMapping("/accept")
    fun accept(@RequestParam("carId") carId: Int, @RequestParam("userId") userId: Int) {

    }

    @PostMapping("/start")
    fun start(@RequestParam("carId") carId: Int, @RequestParam("userId") userId: Int) {

    }

    @PostMapping("/finish")
    fun finish(@RequestParam("carId") carId: Int, @RequestParam("userId") userId: Int) {

    }
}