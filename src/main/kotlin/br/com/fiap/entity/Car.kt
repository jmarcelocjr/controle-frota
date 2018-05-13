package br.com.fiap.entity

import org.springframework.data.mongodb.core.mapping.Document
import java.awt.Point

@Document
data class Car(
    var model: String,
    var lastLocation: Point,
    var status: String
)