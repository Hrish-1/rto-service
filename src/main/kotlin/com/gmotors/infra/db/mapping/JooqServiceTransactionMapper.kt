package com.gmotors.infra.db.mapping

import com.gmotors.core.services.ServiceTransactionCreateRequest
import com.gmotors.core.servicetransaction.ServiceTransaction
import com.gmotors.infra.jooq.tables.records.ServicesTransactionsRecord
import org.mapstruct.Mapper
import java.util.*

@Mapper(config = JooqConfig::class)
interface JooqServiceTransactionMapper {
    fun toRecordsFromCreateRequests(requests: List<ServiceTransactionCreateRequest>): List<ServicesTransactionsRecord>

    fun toRecordsFromModels(models: List<ServiceTransaction>): List<ServicesTransactionsRecord>

    fun toModels(records: List<ServicesTransactionsRecord>): List<ServiceTransaction>

    fun toRecordsFromIds(serviceIds: List<UUID>, transactionId: UUID): List<ServicesTransactionsRecord> {
        val requests = serviceIds.map { ServiceTransactionCreateRequest(UUID.randomUUID(), it, transactionId) }
        return toRecordsFromCreateRequests(requests)
    }
}