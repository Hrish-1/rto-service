package com.gmotors.infra.db

import com.gmotors.core.attachments.AttachmentCreateRequest
import com.gmotors.core.attachments.AttachmentRepository
import com.gmotors.infra.db.mapping.JooqAttachmentMapper
import com.gmotors.infra.jooq.enums.EntityType
import com.gmotors.infra.jooq.tables.references.ATTACHMENTS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.*

typealias P = Pair<String?, String?>
typealias M = Map<String?, String?>

@Repository
class JooqAttachmentRepository(
    val dsl: DSLContext,
    val mapper: JooqAttachmentMapper,
): AttachmentRepository {

    override fun create(request: AttachmentCreateRequest) {
        dsl.executeInsert(mapper.toRecord(request))
    }

    override fun listByTransaction(transactionId: UUID): Map<String, String> {
        return dsl.select(ATTACHMENTS.TYPE, ATTACHMENTS.ID, ATTACHMENTS.EXT)
            .from(ATTACHMENTS)
            .where(ATTACHMENTS.ENTITY_TYPE.eq(EntityType.transaction))
            .and(ATTACHMENTS.ENTITY_ID.eq(transactionId))
            .fetch()
            .map { x -> Pair(x.value1()?.name?.lowercase(), "${x.value2()}.${x.value3()}") }
            .fold<P, M>(mapOf()) { acc, curr -> acc + curr }
            .filterKeys { it != null }
            .mapKeys { it.key!! }
            .filterValues { it != null }
            .mapValues { it.value!! }
    }
}