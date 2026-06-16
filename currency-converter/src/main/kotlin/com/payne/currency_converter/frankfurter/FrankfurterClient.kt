package com.payne.currency_converter.frankfurter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class FrankfurterClient(
    @Autowired restClientBuilder: RestClient.Builder
) {
    private val restClient: RestClient = restClientBuilder
        .baseUrl("https://api.frankfurter.dev/v2/rate")
        .build()

    fun getRateByCurrencyPair(baseCurrency: String, quoteCurrency: String): RateResponse {
        return restClient
            .get()
            .uri("/{baseCurrency}/{quoteCurrency}", baseCurrency, quoteCurrency)
            .retrieve()
            .body<RateResponse>()!!
    }
}
