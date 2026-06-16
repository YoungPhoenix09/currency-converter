package com.payne.currency_converter.conversion

import java.time.Instant

data class ConvertResponse(
    val convertedAmount: Float,
    val exchangeRate: Float,
    val rateRetrievalDate: Instant
)
