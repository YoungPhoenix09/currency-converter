package com.payne.currency_converter.conversion

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ConversionPersistenceManagerTest {
    @RelaxedMockK
    lateinit var conversionRepository: ConversionRepository

    @InjectMockKs
    lateinit var conversionPersistenceManager: ConversionPersistenceManager

    @Test
    fun `can save a conversion`() {
        val conversion = Conversion(
            originalCurrency = "USD",
            originalAmount = 1.0F,
            newCurrency = "EUR",
            newAmount = 1.3F,
        )

        every { conversionRepository.save(conversion) } returns conversion.copy(id = 1)

        conversionPersistenceManager.saveConversion(conversion)

        verify { conversionRepository.save(conversion) }
    }

    @Test
    fun `can retrieve conversions`() {
        val conversion = Conversion(
            originalCurrency = "USD",
            originalAmount = 1.0F,
            newCurrency = "EUR",
            newAmount = 1.3F,
        )

        every { conversionRepository.findAll() } returns listOf(conversion)

        val retrievedRate = conversionPersistenceManager.getConversions()

        verify { conversionRepository.findAll() }
    }
}
