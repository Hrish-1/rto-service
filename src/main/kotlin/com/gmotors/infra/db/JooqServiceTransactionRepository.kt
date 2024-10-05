package com.gmotors.infra.db

import com.gmotors.core.servicetransaction.ServiceTransaction
import com.gmotors.core.servicetransaction.ServiceTransactionRepository
import com.gmotors.infra.db.mapping.JooqServiceTransactionMapper
import com.gmotors.infra.jooq.tables.references.SERVICES_TRANSACTIONS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JooqServiceTransactionRepository(
    private val dsl: DSLContext,
    private val mapper: JooqServiceTransactionMapper) : ServiceTransactionRepository {

    override fun findByTransactionId(transactionId: UUID): List<ServiceTransaction> {
        return mapper.toModels(dsl.selectFrom(SERVICES_TRANSACTIONS)
            .where(SERVICES_TRANSACTIONS.TRANSACTION_ID.eq(transactionId))
            .fetch()
            .toList())
    }
}