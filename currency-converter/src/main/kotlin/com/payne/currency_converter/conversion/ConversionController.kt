package com.payne.currency_converter.conversion

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConversionController(
    private val conversionCalculator: ConversionCalculator,
    private val conversionPersistenceManager: ConversionPersistenceManager,
) {
    @RequestMapping("/convert")
    @PostMapping
    fun convert(@RequestBody convertRequest: ConvertRequest): ConvertResponse {
        return conversionCalculator.convert(convertRequest.originalCurrency, convertRequest.newCurrency, convertRequest.amount)
    }

    @RequestMapping("/conversions")
    @GetMapping
    fun getConversion(): List<Conversion> {
        return conversionPersistenceManager.getConversions()
    }
}
