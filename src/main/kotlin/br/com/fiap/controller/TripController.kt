package br.com.fiap.controller

import br.com.fiap.entity.User
import org.json.JSONObject
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import kotlin.collections.HashMap

@RestController
@RequestMapping("/trip")
class TripController {

    @GetMapping("/cost")
    fun cost(
        @RequestParam("fromLatitude") fromLatitude: String,
        @RequestParam("fromLongitude") fromLongitude: String,
        @RequestParam("toLatitude") toLatitude: String,
        @RequestParam("toLongitude") toLongitude: String
    ): Map<String, String> {
        val valueKilometer = 2.00
        val valueMinute = 1.00

        val url = "https://maps.googleapis.com/maps/api/directions/json?" +
            "origin="+fromLatitude+","+fromLongitude+
            "&destination="+toLatitude+","+toLongitude+"&mode=driving&units=metric&key=AIzaSyCEGLc7gR-7Ll229fcpLIjdh5yMfqqvvbg"

        var restTemplate = RestTemplate()
        val response = restTemplate.getForObject(url, String::class.java)
        var jsonObject  = JSONObject(response)

        val leg = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0)

        val distanceKm = leg.getJSONObject("distance").get("value").toString().toDouble() / 1000
        val durationMinutes = leg.getJSONObject("duration").get("value").toString().toInt() / 60

        var cost = (valueKilometer * distanceKm) + (valueMinute * durationMinutes)

        val result = HashMap<String, String>()
        result.put("value", cost.toString())

        return result
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