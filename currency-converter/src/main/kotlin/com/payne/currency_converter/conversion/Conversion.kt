package com.payne.currency_converter.conversion

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.Instant

@Entity
data class Conversion(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(nullable = false)
    val originalCurrency: String,

    @Column(nullable = false)
    val originalAmount: Float,

    @Column(nullable = false)
    val newCurrency: String,

    @Column(nullable = false)
    val newAmount: Float,

    @Column(nullable = false)
    val createdDate: Instant? = Instant.now()
)
