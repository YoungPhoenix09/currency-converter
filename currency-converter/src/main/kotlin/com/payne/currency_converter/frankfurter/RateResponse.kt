package com.payne.currency_converter.frankfurter

import java.time.LocalDate

data class RateResponse(
    val base: String,
    val quote: String,
    val date: LocalDate,
    val rate: Float
)
