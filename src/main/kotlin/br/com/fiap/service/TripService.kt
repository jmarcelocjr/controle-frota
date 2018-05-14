package br.com.fiap.service

import br.com.fiap.entity.Coordinate
import br.com.fiap.entity.Trip
import br.com.fiap.repository.TripRepository
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class TripService {
    @Autowired
    lateinit var tripRepository: TripRepository

    fun save(trip: Trip) {
        tripRepository.save(trip)
    }

    fun delete(trip: Trip) {
        tripRepository.delete(trip)
    }

    fun getDistanceAndDuration(fromCoordinate: Coordinate, toCoordinate: Coordinate): JSONObject {
        val url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin="+fromCoordinate.latitude+","+fromCoordinate.longitude+
                "&destination="+toCoordinate.latitude+","+toCoordinate.longitude+
                "&mode=driving&units=metric&key=AIzaSyCEGLc7gR-7Ll229fcpLIjdh5yMfqqvvbg"

        var restTemplate = RestTemplate()
        val response = restTemplate.getForObject(url, String::class.java)
        var jsonObject  = JSONObject(response)

        return jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0)
    }
}