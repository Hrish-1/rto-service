package com.gmotors.core.transactions

import com.gmotors.core.PagedItems
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionService(val repository: TransactionRepository) {

    fun create(request: TransactionCreateRequest) {
        repository.create(request)
    }

    fun list(query: TransactionQuery): PagedItems<Transaction> {
        return repository.list(query)
    }

    fun update(request: TransactionUpdateRequest) {
        repository.update(request)
    }

    fun delete(id: UUID) {
        repository.delete(id)
    }
}