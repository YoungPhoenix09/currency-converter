package com.payne.currency_converter.rate

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant

@SpringBootTest
class RateRepositoryTest(
    @Autowired val rateRepository: RateRepository
) {
    @Test
    fun `can save and retrieve rate`() {
        rateRepository.save(Rate(
            baseCurrency="",
            quoteCurrency="",
            rate=0F,
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
            rate=1.3F,
            lastModifiedDate=Instant.now(),
        ))
        val rate = rateRepository.findByBaseCurrencyAndQuoteCurrency("USD", "EUR")
        assertNotNull(rate)
    }
}
