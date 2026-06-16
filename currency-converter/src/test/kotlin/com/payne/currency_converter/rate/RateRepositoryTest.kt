package com.payne.currency_converter.rate

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant

@SpringBootTest
class RateRepositoryTest(
    @Autowired val rateRepository: RateRepository
) {
    @BeforeEach
    fun cleanUp() {
        rateRepository.deleteAll()
    }

    @Test
    fun `can save and retrieve rate`() {
        rateRepository.save(Rate(
            baseCurrency="",
            quoteCurrency="",
            amount=0F,
            lastModifiedDate=Instant.now(),
        ))
        val rates = rateRepository.findAll()
        assertEquals(1, rates.size)
    }

    @Test
    fun `can retrieve rate by base and quote currency pair`() {
        rateRepository.save(Rate(
            baseCurrency="USD",
            quoteCurrency="EUR",
            amount=1.3F,
            lastModifiedDate=Instant.now(),
        ))
        val rate = rateRepository.findByBaseCurrencyAndQuoteCurrency("USD", "EUR")
        assertNotNull(rate)
    }

    @Test
    fun `can update amount by base and quote currency pair`() {
        rateRepository.save(Rate(
            baseCurrency="USD",
            quoteCurrency="EUR",
            amount=1.3F,
            lastModifiedDate=Instant.now(),
        ))
        rateRepository.updateRateByBaseCurrencyAndQuoteCurrency("USD", "EUR", 1.4F)
        val rate = rateRepository.findByBaseCurrencyAndQuoteCurrency("USD", "EUR")
        assertEquals(1.4F, rate?.amount)
    }
}
