package br.com.fiap.entity

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Car(
    var model: String,
    var lastLocation: Coordinate,
    var status: String
)