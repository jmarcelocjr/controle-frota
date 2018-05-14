package br.com.fiap.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Trip (
    @Id var id: Int?,
    var user: User,
    var car: Car,
    var startedAt: Date,
    var finishedAt: Date,
    var from: Coordinate,
    var to: Coordinate,
    var value: Float,
    var paymentMethod: String
)