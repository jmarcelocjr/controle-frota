package br.com.fiap.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
        @Id var id: String?,
        var name: String,
        var login: String,
        var password: String,
        var paymentMethod: String
)