package br.com.fiap.controller

import br.com.fiap.entity.Car
import br.com.fiap.entity.Coordinate
import br.com.fiap.entity.User
import br.com.fiap.service.CarService
import br.com.fiap.service.TripService
import br.com.fiap.service.UserService
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import kotlin.collections.HashMap

@RestController
@RequestMapping("/trip")
class TripController {

    @Autowired
    lateinit var carService: CarService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var tripService: TripService

    @GetMapping("/cost")
    fun cost(
        @RequestParam("fromLatitude") fromLatitude: Double,
        @RequestParam("fromLongitude") fromLongitude: Double,
        @RequestParam("toLatitude") toLatitude: Double,
        @RequestParam("toLongitude") toLongitude: Double
    ): Map<String, String> {
        val valueKilometer = 2.00
        val valueMinute = 1.00

        val fromCoordinate = Coordinate(fromLatitude, fromLongitude)
        val toCoordinate = Coordinate(toLatitude, toLongitude)

        val distanceAndDuration = tripService.getDistanceAndDuration(fromCoordinate, toCoordinate)

        val distanceKm = distanceAndDuration.getJSONObject("distance").getDouble("value") / 1000
        val durationMinutes = distanceAndDuration.getJSONObject("duration").getInt("value") / 60

        var cost = (valueKilometer * distanceKm) + (valueMinute * durationMinutes)

        val result = HashMap<String, String>()
        result.put("value", cost.toString())

        return result
    }

    @PostMapping("/request")
    fun request(@RequestBody jsonString: String) {
        val json = JSONObject(jsonString)

        val user = userService.findById(json.getString("carId"))

        val cars = carService.findByStatus(Car.STATUS_AVAIABLE)

        if(cars == null) {
            return
        }

        val location = Coordinate(
            json.getJSONObject("location").getDouble("latitude"),
            json.getJSONObject("location").getDouble("longitude")
        )

        var nearest = 99999.00
        lateinit var choosedCar: Car

        for(car in cars!!){
            val distanceDuration = tripService.getDistanceAndDuration(location, car.lastLocation)

            val distance = distanceDuration.getJSONObject("distance").getDouble("value") / 1000

            if (distance < nearest) {
                nearest = distance
                choosedCar = car
            }
        }

        carService.sendRequestTrip(choosedCar, user!!.id!!, location)
    }

    @PostMapping("/accept")
    fun accept(@RequestParam("carId") carId: String, @RequestParam("userId") userId: String) {

    }

    @PostMapping("/start")
    fun start(@RequestParam("carId") carId: String, @RequestParam("userId") userId: String) {

    }

    @PostMapping("/finish")
    fun finish(@RequestParam("carId") carId: Int, @RequestParam("userId") userId: Int) {

    }
}