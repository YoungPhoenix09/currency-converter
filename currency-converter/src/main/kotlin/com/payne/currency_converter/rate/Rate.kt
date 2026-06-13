package com.payne.currency_converter.rate

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.Instant

@Entity
data class Rate(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(nullable = false)
    val baseCurrency: String,

    @Column(nullable = false)
    val quoteCurrency: String,

    @Column(nullable = false)
    val rate: Float,

    @Column(nullable = false)
    val lastModifiedDate: Instant,

    @Column(nullable = false)
    val refreshRateInMillis: Int = 3600000,
)
