package com.payne.currency_converter.conversion

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConversionRepository : JpaRepository<Conversion, Long> {
    fun findAllByOrderByCreatedDateDesc(): List<Conversion>
}
