package com.gmotors.core.transactions

import com.gmotors.core.PagedItems
import java.util.*

interface TransactionRepository {

    fun create(request: TransactionCreateRequest)

    fun update(request: TransactionUpdateRequest)

    fun list(query: TransactionQuery): PagedItems<Transaction>

    fun delete(id: UUID)

    fun get(id: UUID): Transaction?
}