package com.gmotors.core.transactions

import java.time.LocalDate
import java.util.*

data class TransactionCreateRequest(
    val id: UUID,
    val status: Status,
    val vehicleNumber: String,
    val fromRto: String,
    val toRto: String,
    val customerId: UUID,
    val createdBy: UUID,
    val serviceIds: Set<UUID> = setOf(),
    val chassisNumber: String? = null,
    val letterNumber: String? = null,
    val letterDate: LocalDate? = null,
    val note: String? = null,
    val seller: Seller? = null,
    val purchaser: Purchaser? = null,
    val challanPayment: Double? = null,
    val challanNumber: Int? = null,
    val officerPayment: Double? = null,
    val cancelDate: LocalDate? = null,
    val bankId: UUID? = null
)
