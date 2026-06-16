package com.payne.currency_converter.conversion

import com.payne.currency_converter.rate.RatePersistenceManager
import org.springframework.stereotype.Service

@Service
class ConversionCalculator(
    private val conversionPersistenceManager: ConversionPersistenceManager,
    private val ratePersistenceManager: RatePersistenceManager,
) {
    fun convert(baseCurrency: String, quoteCurrency: String, baseAmount: Float): ConvertResponse {
        val rate = ratePersistenceManager.getRateByBaseAndQuoteCurrency(baseCurrency, quoteCurrency)

        return rate?.let {
            val conversion = conversionPersistenceManager.saveConversion(Conversion(
                originalCurrency = baseCurrency,
                originalAmount = baseAmount,
                newCurrency = quoteCurrency,
                newAmount = baseAmount * rate.amount
            ))
            ConvertResponse(
                convertedAmount = conversion.newAmount,
                exchangeRate = rate.amount,
                rateRetrievalDate = rate.lastModifiedDate
            )
        } ?: throw Exception("Failed to fetch the rate.")
    }
}
