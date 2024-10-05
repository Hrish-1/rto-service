package com.gmotors.core.servicetransaction

import java.util.UUID;

data class ServiceTransaction(
    val id: UUID,
    val transactionId: UUID,
    val serviceId: UUID
)
