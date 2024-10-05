package com.gmotors.core.transactions

import com.gmotors.core.Auditable
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

data class Transaction(
    val id: UUID,
    val status: Status,
    val vehicleNumber: String,
    val fromRto: String,
    val toRto: String,
    val amount: Double?,
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
    override val createdAt: OffsetDateTime,
    override val updatedAt: OffsetDateTime?,
) : Auditable

data class Seller(
    val name: String?,
    val so: String?,
    val mobile: String?,
    val address: String?
)

data class Purchaser(
    val name: String?,
    val so: String?,
    val mobile: String?,
    val address: String?
)

enum class Status {
    READY,
    DELIVERED,
    INVOICED,
    COMPLETED
}
