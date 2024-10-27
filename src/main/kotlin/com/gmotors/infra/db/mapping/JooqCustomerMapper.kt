package com.gmotors.infra.db.mapping

import com.gmotors.core.customers.CustomerCreateRequest
import com.gmotors.infra.jooq.tables.records.CustomersRecord
import org.mapstruct.Mapper

@Mapper(config = JooqConfig::class)
interface JooqCustomerMapper {
    fun toRecord(request: CustomerCreateRequest): CustomersRecord
}