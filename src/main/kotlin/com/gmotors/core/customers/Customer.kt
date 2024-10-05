package com.gmotors.core.customers

import com.gmotors.core.Auditable
import java.time.OffsetDateTime
import java.util.UUID

data class Customer(
    val id: UUID,
    val name: String,
    val company: String?,
    val address: String?,
    val city: String?,
    val pinCode: String?,
    val state: String?,
    val stateCode: String?,
    val contact1: String?,
    val contact2: String?,
    val emailId: String?,
    val enteredBy: UUID,
    val updatedBy: UUID?,
    override val createdAt: OffsetDateTime,
    override val updatedAt: OffsetDateTime?,
) : Auditable