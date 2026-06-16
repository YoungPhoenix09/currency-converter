package com.payne.currency_converter.conversion

import org.springframework.stereotype.Service

@Service
class ConversionPersistenceManager(
    private val conversionRepository: ConversionRepository,
) {
    fun saveConversion(conversion: Conversion) =
        conversionRepository.save(conversion)


    fun getConversions() =
        conversionRepository.findAll()
}
