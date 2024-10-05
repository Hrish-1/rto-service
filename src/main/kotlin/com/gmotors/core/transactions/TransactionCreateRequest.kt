package com.gmotors.core.transactions

import java.time.LocalDate
import java.util.*

data class TransactionCreateRequest(
    val id: UUID,
    val status: Status,
    val vehicleNumber: String,
    val fromRto: String,
    val toRto: String,
    val chassisNumber: String?,
    val letterNumber: String?,
    val letterDate: LocalDate?,
    val note: String?,
    val seller: Seller?,
    val purchaser: Purchaser?,
    val challanPayment: Double?,
    val challanNumber: Int?,
    val officerPayment: Double?,
    val cancelDate: LocalDate?,
    val customerId: UUID,
    val bankId: UUID?,
    val createdBy: UUID,
    val serviceIds: List<UUID> = listOf()
)
