package com.payne.currency_converter.rate

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface RateRepository : JpaRepository<Rate, Long> {
    fun findByBaseCurrencyAndQuoteCurrency(baseCurrency: String, quoteCurrency: String): Rate?

    @Transactional
    @Modifying
    @Query("update Rate r set r.amount = ?3, r.lastModifiedDate = CURRENT_TIMESTAMP where r.baseCurrency = ?1 and r.quoteCurrency = ?2")
    fun updateRateByBaseCurrencyAndQuoteCurrency(baseCurrency: String, quoteCurrency: String, amount: Float)
}
