package com.gmotors.infra.db

import com.gmotors.core.employees.EmployeeCreateRequest
import com.gmotors.core.employees.EmployeeRepository
import com.gmotors.infra.db.mapping.JooqEmployeeMapper
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class JooqEmployeeRepository(
    val dsl: DSLContext,
    val mapper: JooqEmployeeMapper
) : EmployeeRepository {

    override fun create(request: EmployeeCreateRequest) {
        dsl.executeInsert(mapper.toRecord(request))
    }
}