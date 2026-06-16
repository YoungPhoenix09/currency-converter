package com.payne.currency_converter.conversion

import com.payne.currency_converter.rate.Rate
import com.payne.currency_converter.rate.RatePersistenceManager
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ConversionCalculatorTest {
    @RelaxedMockK
    lateinit var conversionPersistenceManager: ConversionPersistenceManager

    @RelaxedMockK
    lateinit var ratePersistenceManager: RatePersistenceManager

    @InjectMockKs
    lateinit var conversionCalculator: ConversionCalculator

    @Test
    fun `it can convert between two currencies`() {
        val baseCurrency = "EUR"
        val quoteCurrency = "USD"

        every { ratePersistenceManager.getRateByBaseAndQuoteCurrency(any(), any()) } returns Rate(
            baseCurrency = baseCurrency,
            quoteCurrency = quoteCurrency,
            amount = 0.5F,
        )
        conversionCalculator.convert(baseCurrency, quoteCurrency, 20F)

        verify(atLeast = 1) { ratePersistenceManager.getRateByBaseAndQuoteCurrency(baseCurrency, quoteCurrency) }
    }

    @Test
    fun `it saves the conversion after calculating`() {
        val baseCurrency = "EUR"
        val quoteCurrency = "USD"
        val conversionSlot = slot<Conversion>()

        every { ratePersistenceManager.getRateByBaseAndQuoteCurrency(any(), any()) } returns Rate(
            baseCurrency = baseCurrency,
            quoteCurrency = quoteCurrency,
            amount = 0.5F,
        )

        conversionCalculator.convert(baseCurrency, quoteCurrency, 20F)

        verify(atLeast = 1) { conversionPersistenceManager.saveConversion(capture(conversionSlot)) }

        assertEquals(10.0F, conversionSlot.captured.newAmount)
    }
}
