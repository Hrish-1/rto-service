package com.gmotors.infra.db

import com.gmotors.core.services.ServiceRepository
import com.gmotors.infra.jooq.tables.references.SERVICES
import org.jooq.DSLContext
import org.jooq.impl.DSL.sum
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JooqServiceRepository(private val dsl: DSLContext) : ServiceRepository {

    override fun totalAmount(ids: List<UUID>): Double =
        dsl.select(sum(SERVICES.AMOUNT))
            .from(SERVICES)
            .where(SERVICES.ID.`in`(ids))
            .sumOf { it.value1() }
            .toDouble()
}