package br.com.fiap.service

import br.com.fiap.entity.Car
import br.com.fiap.entity.Coordinate
import br.com.fiap.entity.Trip
import br.com.fiap.repository.CarRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class CarService {
    @Autowired
    lateinit var carRepository: CarRepository

    fun findById(id: String): Car? {
        return carRepository.findById(id).orElse(null)
    }

    fun findByStatus(status: String): List<Car>? {
        return carRepository.findByStatus(status)
    }

    fun register(car: Car) {
        carRepository.save(car)
    }

    fun sendRequestTrip(car: Car, id: String, trip: Trip){
        System.out.println("-----Sending request trip to car-----")

        //simulating a  time to request
        TimeUnit.SECONDS.sleep(1);

        System.out.println("-----Sended-----")
    }

    fun moveTo(car: Car, location: Coordinate) {
        System.out.println("-----Moving car to the destination-----")
    }
}