package com.gmotors.core.servicetransaction

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ServiceTransactionService(private val repository: ServiceTransactionRepository) {

    fun listByTransactionId(transactionId: UUID): List<ServiceTransaction> {
        return repository.findByTransactionId(transactionId)
    }
}