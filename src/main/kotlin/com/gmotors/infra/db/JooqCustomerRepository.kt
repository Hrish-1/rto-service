package com.gmotors.infra.db

import com.gmotors.core.customers.CustomerCreateRequest
import com.gmotors.core.customers.CustomerRepository
import com.gmotors.infra.db.mapping.JooqCustomerMapper
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class JooqCustomerRepository(
    val dsl: DSLContext,
    val mapper: JooqCustomerMapper
) : CustomerRepository {

    override fun create(request: CustomerCreateRequest) {
        dsl.executeInsert(mapper.toRecord(request))
    }
}