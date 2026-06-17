package com.payne.currency_converter.conversion

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ConversionRepositoryTest(
    @Autowired val conversionRepository: ConversionRepository
) {
    @BeforeEach
    fun cleanUp() {
        conversionRepository.deleteAll()
    }

    @Test
    fun `can save and retrieve conversion`() {
        conversionRepository.save(Conversion(
            originalCurrency = "USD",
            originalAmount = 1F,
            newCurrency = "EURO",
            newAmount = 1.5F,
        ))
        val conversions = conversionRepository.findAllByOrderByCreatedDateDesc()
        assertEquals(1, conversions.size)
    }
}
