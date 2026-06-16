package com.payne.currency_converter.rate

import com.payne.currency_converter.frankfurter.FrankfurterClient
import com.payne.currency_converter.frankfurter.RateResponse
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@ExtendWith(MockKExtension::class)
class RatePersistenceManagerTest {
    @RelaxedMockK
    lateinit var rateRepository: RateRepository

    @RelaxedMockK
    lateinit var frankfurterClient: FrankfurterClient

    @InjectMockKs
    lateinit var ratePersistenceManager: RatePersistenceManager

    @Test
    fun `can save a rate`() {
        val rate = Rate(
            baseCurrency = "USD",
            quoteCurrency = "EUR",
            amount = 1.3F,
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
            amount = 1.3F,
        )

        every { rateRepository.findByBaseCurrencyAndQuoteCurrency("USD", "EUR") } returns rate

        val retrievedRate = ratePersistenceManager.getRateByBaseAndQuoteCurrency("USD", "EUR")

        verify { rateRepository.findByBaseCurrencyAndQuoteCurrency("USD", "EUR") }

        assertEquals("USD", retrievedRate?.baseCurrency)
        assertEquals("EUR", retrievedRate?.quoteCurrency)
    }

    @Test
    fun `calls frankfurter and caches values before returning rate if expired`() {
        val rate = Rate(
            id = 1,
            baseCurrency = "USD",
            quoteCurrency = "EUR",
            amount = 1.3F,
            lastModifiedDate = Instant.now().minus(10, ChronoUnit.MILLIS),
            refreshRateInMillis = 5,
        )

        every { frankfurterClient.getRateByCurrencyPair("USD", "EUR") } returns RateResponse(
            base = "USD",
            quote = "EUR",
            date = LocalDate.now(),
            rate = 1.4F
        )
        every { rateRepository.findByBaseCurrencyAndQuoteCurrency("USD", "EUR") } returnsMany listOf(
            rate,
            rate.copy(
                amount = 1.4F,
                lastModifiedDate = Instant.now()
            )
        )

        val retrievedRate = ratePersistenceManager.getRateByBaseAndQuoteCurrency("USD", "EUR")!!

        verify(exactly = 1) { frankfurterClient.getRateByCurrencyPair("USD", "EUR") }
        verify(exactly = 1) { rateRepository.updateRateByBaseCurrencyAndQuoteCurrency("USD", "EUR", 1.4F) }
        verify(exactly = 2) { rateRepository.findByBaseCurrencyAndQuoteCurrency("USD", "EUR") }

        assertEquals("USD", retrievedRate.baseCurrency)
        assertEquals("EUR", retrievedRate.quoteCurrency)
        assertNotEquals(rate.lastModifiedDate, retrievedRate.lastModifiedDate)
        assertEquals(1.4F, retrievedRate.amount)
    }

    @Test
    fun `simply returns rate if not expired`() {
        val rate = Rate(
            id = 1,
            baseCurrency = "USD",
            quoteCurrency = "EUR",
            amount = 1.3F,
            lastModifiedDate = Instant.now(),
            refreshRateInMillis = 5000,
        )

        every { frankfurterClient.getRateByCurrencyPair("USD", "EUR") } returns RateResponse(
            base = "USD",
            quote = "EUR",
            date = LocalDate.now(),
            rate = 1.4F
        )
        every { rateRepository.findByBaseCurrencyAndQuoteCurrency("USD", "EUR") } returnsMany listOf(
            rate,
            rate.copy(
                amount = 1.4F,
                lastModifiedDate = Instant.now()
            )
        )

        val retrievedRate = ratePersistenceManager.getRateByBaseAndQuoteCurrency("USD", "EUR")!!

        verify(exactly = 0) { frankfurterClient.getRateByCurrencyPair("USD", "EUR") }
        verify(exactly = 0) { rateRepository.updateRateByBaseCurrencyAndQuoteCurrency("USD", "EUR", 1.4F) }
        verify(exactly = 1) { rateRepository.findByBaseCurrencyAndQuoteCurrency("USD", "EUR") }

        assertEquals("USD", retrievedRate.baseCurrency)
        assertEquals("EUR", retrievedRate.quoteCurrency)
        assertEquals(1.3F, retrievedRate.amount)
    }
}
