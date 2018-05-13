package br.com.fiap.repository

import br.com.fiap.entity.Trip
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TripRepository : MongoRepository<Trip, String>