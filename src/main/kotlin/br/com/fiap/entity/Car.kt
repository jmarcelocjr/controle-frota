package br.com.fiap.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Car(
        @Id var id: Int,
        var model: String,
        var lastLocation: Coordinate,
        var status: String
)