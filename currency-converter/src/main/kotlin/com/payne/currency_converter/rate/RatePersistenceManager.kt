package com.payne.currency_converter.rate

import org.springframework.stereotype.Service

@Service
class RatePersistenceManager(
    private val rateRepository: RateRepository
) {
    fun saveRate(rate: Rate) {
        rateRepository.save(rate)
    }

    fun getRateByBaseAndQuoteCurrency(baseCurrency: String, quoteCurrency: String): Rate? =
        rateRepository.findByBaseCurrencyAndQuoteCurrency(baseCurrency, quoteCurrency)
}
