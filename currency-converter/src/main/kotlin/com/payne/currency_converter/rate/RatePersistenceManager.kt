package com.payne.currency_converter.rate

import com.payne.currency_converter.frankfurter.FrankfurterClient
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class RatePersistenceManager(
    private val rateRepository: RateRepository,
    private val frankfurterClient: FrankfurterClient,
) {
    fun saveRate(rate: Rate) {
        rateRepository.save(rate)
    }

    fun getRateByBaseAndQuoteCurrency(baseCurrency: String, quoteCurrency: String): Rate? {
        val currentTime = Instant.now()
        val currentRate = rateRepository.findByBaseCurrencyAndQuoteCurrency(baseCurrency, quoteCurrency)

        if (currentRate != null && currentRate.lastModifiedDate.until(currentTime, ChronoUnit.MILLIS) < currentRate.refreshRateInMillis) {
            return currentRate
        }

        val rateResponse = frankfurterClient.getRateByCurrencyPair(baseCurrency, quoteCurrency)

        if (currentRate == null) {
            return rateRepository.save(Rate(
                baseCurrency = rateResponse.base,
                quoteCurrency = rateResponse.quote,
                amount = rateResponse.rate,
            ))
        } else {
            rateRepository.updateRateByBaseCurrencyAndQuoteCurrency(baseCurrency, quoteCurrency, rateResponse.rate)
            return rateRepository.findByBaseCurrencyAndQuoteCurrency(baseCurrency, quoteCurrency)
        }
    }
}
