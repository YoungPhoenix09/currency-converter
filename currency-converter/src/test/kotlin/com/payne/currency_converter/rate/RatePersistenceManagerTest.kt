package com.payne.currency_converter.rate

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class RatePersistenceManagerTest {
    @RelaxedMockK
    lateinit var rateRepository: RateRepository

    @InjectMockKs
    lateinit var ratePersistenceManager: RatePersistenceManager

    @Test
    fun `can save a rate`() {
        val rate = Rate(
            baseCurrency = "USD",
            quoteCurrency = "EUR",
            rate = 1.3F,
        )

        every { rateRepository.save(rate) } returns rate.copy(id = 1)

        ratePersistenceManager.saveRate(rate)

        verify { rateRepository.save(rate) }
    }

    @Test
    fun `can retrieve a rate by base and quote`() {
        val rate = Rate(
            id = 1,
            baseCurrency = "USD",
            quoteCurrency = "EUR",
            rate = 1.3F,
        )

        every { rateRepository.findByBaseCurrencyAndQuoteCurrency("USD", "EUR") } returns rate

        val retrievedRate = ratePersistenceManager.getRateByBaseAndQuoteCurrency("USD", "EUR")

        verify { rateRepository.findByBaseCurrencyAndQuoteCurrency("USD", "EUR") }

        assertEquals("USD", retrievedRate?.baseCurrency)
        assertEquals("EUR", retrievedRate?.quoteCurrency)
    }
}
