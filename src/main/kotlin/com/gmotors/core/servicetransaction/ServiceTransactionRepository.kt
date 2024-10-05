package com.gmotors.core.servicetransaction

import java.util.UUID

interface ServiceTransactionRepository {
    fun findByTransactionId(transactionId: UUID): List<ServiceTransaction>
}