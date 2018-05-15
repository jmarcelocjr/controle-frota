package br.com.fiap.controller

import br.com.fiap.entity.Car
import br.com.fiap.entity.Coordinate
import br.com.fiap.entity.Trip
import br.com.fiap.service.CarService
import br.com.fiap.service.TripService
import br.com.fiap.service.UserService
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
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
        val fromLocation = Coordinate(fromLatitude, fromLongitude)
        val toLocation = Coordinate(toLatitude, toLongitude)

        val result = HashMap<String, String>()
        result.put("value", getCost(fromLocation, toLocation).toString())

        return result
    }

    private fun getCost(
            fromLocation: Coordinate,
            toLocation: Coordinate
    ): Double {
        val valueKilometer = 2.00
        val valueMinute = 1.00

        val distanceAndDuration = tripService.getDistanceAndDuration(fromLocation, toLocation)

        val distanceKm = distanceAndDuration.getJSONObject("distance").getDouble("value") / 1000
        val durationMinutes = distanceAndDuration.getJSONObject("duration").getInt("value") / 60

        return (valueKilometer * distanceKm) + (valueMinute * durationMinutes)
    }

    @PostMapping("/request")
    fun request(@RequestBody jsonString: String): ResponseEntity<Trip?> {
        val json = JSONObject(jsonString)

        val user = userService.findById(json.getString("userId"))

        val cars = carService.findByStatus(Car.STATUS_AVAIABLE)

        if(user == null) {
            return ResponseEntity(HttpStatus.FORBIDDEN)
        }

        if(cars == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val fromLocation = Coordinate(
            json.getJSONObject("from").getDouble("latitude"),
            json.getJSONObject("from").getDouble("longitude")
        )

        val toLocation = Coordinate(
            json.getJSONObject("to").getDouble("latitude"),
            json.getJSONObject("to").getDouble("longitude")
        )

        var nearest = 99999.00
        lateinit var choosedCar: Car

        for(car in cars){
            val distanceDuration = tripService.getDistanceAndDuration(fromLocation, car.lastLocation!!)

            val distance = distanceDuration.getJSONObject("distance").getDouble("value") / 1000

            if (distance < nearest) {
                nearest = distance
                choosedCar = car
            }
        }

        val cost = getCost(fromLocation, toLocation)

        val trip = Trip(
                null,
                user,
                choosedCar,
                Date(),
                null,
                null,
                fromLocation,
                toLocation,
                cost,
                user.paymentMethod,
                Trip.STATUS_NEW
        )
        tripService.save(trip)

        carService.sendRequestTrip(choosedCar, user.id!!, trip)

        return ResponseEntity(trip, HttpStatus.CREATED)
    }

    @GetMapping("/status/{id}")
    fun status(@PathVariable("id", required=true) id: String): Trip? {
        return tripService.findById(id)
    }

    @PostMapping("/accept")
    fun accept(@RequestBody(required = true) jsonString: String): ResponseEntity<Void> {
        val jsonObject = JSONObject(jsonString)

        val car = carService.findById(jsonObject.getString("carId"))
        val trip = tripService.findById(jsonObject.getString("tripId"))

        if(car == null || trip == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        car.status = Car.STATUS_IN_TRANSIT
        carService.register(car)

        trip.status = Trip.STATUS_IN_TRANSIT_TO_PICKUP
        trip.car = car
        tripService.save(trip)

        carService.moveTo(car, trip.from)

        userService.notifyTripChangeStatus(trip.user, Trip.STATUS_IN_TRANSIT_TO_PICKUP)

        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("/start")
    fun start(@RequestBody(required = true) jsonString: String): ResponseEntity<Void> {
        val jsonObject = JSONObject(jsonString)

        val car = carService.findById(jsonObject.getString("carId"))
        val trip = tripService.findById(jsonObject.getString("tripId"))

        if(car == null || trip == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        car.status = Car.STATUS_IN_PROGRESS
        carService.register(car)

        trip.startedAt = Date()
        trip.status = Trip.STATUS_IN_PROGRESS
        trip.car = car
        tripService.save(trip)

        carService.moveTo(car, trip.to)

        userService.notifyTripChangeStatus(trip.user, Trip.STATUS_IN_PROGRESS)

        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("/finish")
    fun finish(@RequestBody(required = true) jsonString: String): ResponseEntity<Void> {
        val jsonObject = JSONObject(jsonString)

        val car = carService.findById(jsonObject.getString("carId"))
        val trip = tripService.findById(jsonObject.getString("tripId"))

        if(car == null || trip == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        car.status = Car.STATUS_AVAIABLE
        carService.register(car)

        trip.finishedAt = Date()
        trip.status = Trip.STATUS_FINISHED
        tripService.save(trip)

        userService.notifyTripChangeStatus(trip.user, Trip.STATUS_FINISHED)
        userService.requestPayment(trip.user, trip.value)

        return ResponseEntity(HttpStatus.OK)
    }
}