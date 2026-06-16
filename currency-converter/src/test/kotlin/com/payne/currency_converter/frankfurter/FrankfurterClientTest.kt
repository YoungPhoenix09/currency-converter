package com.payne.currency_converter.frankfurter

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FrankfurterClientTest(
    @Autowired val frankfurterClient: FrankfurterClient
) {
    @Test
    fun `can fetch from Frankfurter API`() {
        val response = frankfurterClient.getRateByCurrencyPair("EUR", "USD")
        assertEquals("EUR", response.base)
        assertEquals("USD", response.quote)
        assertNotNull(response.rate)
    }
}
