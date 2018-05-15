package br.com.fiap.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Trip (
    @Id var id: String?,
    var user: User,
    var car: Car,
    var requestedAt: Date,
    var startedAt: Date?,
    var finishedAt: Date?,
    var from: Coordinate,
    var to: Coordinate,
    var value: Double,
    var paymentMethod: String,
    var status: String
) {
    companion object {
        val STATUS_NEW = "new"
        val STATUS_AWAITING_ACCEPT = "awaiting_accept"
        val STATUS_IN_TRANSIT_TO_PICKUP = "in_transit_to_pickup"
        val STATUS_IN_PROGRESS = "in_progress"
        val STATUS_FINISHED = "finish"
        val STATUS_CANCELED = "canceled"
    }
}