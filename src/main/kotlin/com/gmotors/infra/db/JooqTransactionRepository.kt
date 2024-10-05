package com.gmotors.infra.db

import com.gmotors.core.PagedItems
import com.gmotors.core.services.ServiceRepository
import com.gmotors.core.servicetransaction.ServiceTransactionRepository
import com.gmotors.core.transactions.*
import com.gmotors.infra.db.mapping.JooqServiceTransactionMapper
import com.gmotors.infra.db.mapping.JooqTransactionMapper
import com.gmotors.infra.jooq.tables.references.TRANSACTIONS
import lombok.extern.slf4j.Slf4j
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.*

@Slf4j
@Repository
class JooqTransactionRepository(
    private val dsl: DSLContext,
    private val mapper: JooqTransactionMapper,
    private val svcRepository: ServiceRepository,
    private val svcTxMapper: JooqServiceTransactionMapper,
    private val svcTxRepository: ServiceTransactionRepository
) : TransactionRepository {

    override fun create(request: TransactionCreateRequest) {
        dsl.transaction { ctx ->
            val txDsl = ctx.dsl()
            val amount = svcRepository.totalAmount(request.serviceIds)
            val txRecords = mapper.toRecord(request, amount)
            txDsl.executeInsert(txRecords)
            val svcTxRecords = svcTxMapper.toRecordsFromIds(request.serviceIds, request.id)
            txDsl.batchInsert(svcTxRecords).execute()
        }
    }

    override fun update(request: TransactionUpdateRequest) {
        val existingAmount = getAmount(request.id)
        val calculatedAmount = svcRepository.totalAmount(request.serviceIds)
        val updateAmount = existingAmount != calculatedAmount
        dsl.transaction { ctx ->
            val txDsl = ctx.dsl()
            if (!updateAmount) {
                txDsl.executeUpdate(mapper.toRecord(request, existingAmount))
                return@transaction
            }
            txDsl.executeUpdate(mapper.toRecord(request, calculatedAmount))
            val xs = svcTxMapper.toRecordsFromModels(svcTxRepository.findByTransactionId(request.id))
            txDsl.batchDelete(xs).execute()
            val ys = svcTxMapper.toRecordsFromIds(request.serviceIds, request.id)
            txDsl.batchInsert(ys).execute()
        }
    }

    override fun list(query: TransactionQuery): PagedItems<Transaction> {
        val items = dsl
            .selectFrom(TRANSACTIONS)
            .where()
            .apply {
                query.status?.let { this.and(TRANSACTIONS.STATUS.eq(it)) }
                query.page.let { this.offset(it) }
                query.size.let { this.limit(it) }
            }
            .fetch()
            .map(mapper::toModel)
        return pageInfo(query, items)
    }

    override fun delete(id: UUID) {
        dsl
            .deleteFrom(TRANSACTIONS)
            .where(TRANSACTIONS.ID.eq(id))
            .execute()
    }

    override fun get(id: UUID): Transaction? {
        val record = dsl
            .selectFrom(TRANSACTIONS)
            .where(TRANSACTIONS.ID.eq(id))
            .fetchOne()
        return record?.let { mapper.toModel(record) }
    }

    private fun getAmount(id: UUID): Double = dsl
        .select(TRANSACTIONS.AMOUNT)
        .from(TRANSACTIONS)
        .where(TRANSACTIONS.ID.eq(id))
        .fetchOne()
        ?.value1() ?: 0.0

    private fun pageInfo(query: TransactionQuery, items: List<Transaction>): PagedItems<Transaction> {
        val (page, size, status) = query
        val totalItems = dsl
            .selectFrom(TRANSACTIONS)
            .where()
            .apply { status?.let { this.and(TRANSACTIONS.STATUS.eq(it)) } }
            .count()
        val totalPages = 1.coerceAtLeast(totalItems / size)
        val isFirst = page == 0
        val isLast = page == 0.coerceAtLeast(totalPages - 1)
        return PagedItems(items, page, size, isFirst, isLast, totalPages, totalItems)
    }
}