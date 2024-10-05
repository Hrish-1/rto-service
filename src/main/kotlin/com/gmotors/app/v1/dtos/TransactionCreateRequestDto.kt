package com.gmotors.app.v1.dtos

import com.gmotors.core.transactions.Purchaser
import com.gmotors.core.transactions.Seller
import com.gmotors.core.transactions.Status
import java.time.LocalDate
import java.util.*

data class TransactionCreateRequestDto(
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
    val serviceIds: List<UUID> = listOf()
)
