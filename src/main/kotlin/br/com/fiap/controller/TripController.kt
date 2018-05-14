package br.com.fiap.controller

import br.com.fiap.entity.Car
import br.com.fiap.entity.User
import org.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.io.FileInputStream
import java.util.*

@RestController
@RequestMapping("/trip")
class TripController {

    @GetMapping("/cost")
    fun cost(
        @RequestParam("fromLatitude") fromLatitude: String,
        @RequestParam("fromLongitude") fromLongitude: String,
        @RequestParam("toLatitude") toLatitude: String,
        @RequestParam("toLongitude") toLongitude: String
    ) {
        val valueKilometer = 2.00
        val valueMinute = 1.00

        val url = "https://maps.googleapis.com/maps/api/directions/json?" +
            "origin="+fromLatitude+","+fromLongitude+
            "&destination="+toLatitude+","+toLongitude+"&mode=driving&units=metric&key="

        var properties = Properties()
        try{
            val input = FileInputStream("application.properties")
            properties.load(input)
        }catch(e: Exception) {
            System.out.println("Error getting property")
        }

        var restTemplate = RestTemplate()

        val response = restTemplate.getForObject(url + properties.getProperty("api.google.secret.key"), String::class.java)

        var jsonObject  = JSONObject(response)

        val leg = jsonObject.getJSONObject("routes").getJSONArray("legs").getJSONObject(0)

        val distanceKm = leg.getJSONObject("distance").get("value").toString().toInt() / 1000
        val durationMinutes = leg.getJSONObject("duration").get("value").toString().toInt() / 60

        val responseJson = JSONObject()

        var cost = (valueKilometer * distanceKm) + (valueMinute * durationMinutes)

        responseJson.append("value", cost)
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