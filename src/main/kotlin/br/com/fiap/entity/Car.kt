package br.com.fiap.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Car(
        @Id var id: String?,
        var model: String,
        var lastLocation: Coordinate,
        var status: String
) {
    companion object {
        val STATUS_AVAIABLE = "available"
        val STATUS_UNAVAIABLE = "unavailable"
        val STATUS_IN_TRANSIT = "in_transit"
        val STATUS_IN_PROGRESS = "in_progress"
    }
}