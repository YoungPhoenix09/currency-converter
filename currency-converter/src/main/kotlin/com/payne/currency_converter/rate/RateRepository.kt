package com.payne.currency_converter.rate

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RateRepository : JpaRepository<Rate, Long>
