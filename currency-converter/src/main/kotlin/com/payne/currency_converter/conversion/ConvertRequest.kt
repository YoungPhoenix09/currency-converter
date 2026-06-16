package com.payne.currency_converter.conversion

data class ConvertRequest(
    val originalCurrency: String,
    val newCurrency: String,
    val amount: Float,
)
