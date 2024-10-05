package com.gmotors.core.services

import java.util.UUID

data class ServiceTransactionCreateRequest(
    val id: UUID,
    val serviceId: UUID,
    val transactionId: UUID
)
