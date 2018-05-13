package br.com.fiap.service

import br.com.fiap.entity.Trip
import br.com.fiap.repository.TripRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
}