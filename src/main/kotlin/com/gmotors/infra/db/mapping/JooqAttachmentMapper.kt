package com.gmotors.infra.db.mapping

import com.gmotors.core.attachments.AttachmentCreateRequest
import com.gmotors.core.attachments.AttachmentType
import com.gmotors.core.attachments.EntityType
import com.gmotors.infra.jooq.tables.records.AttachmentsRecord
import org.mapstruct.EnumMapping
import org.mapstruct.Mapper

@Mapper(config = JooqConfig::class)
interface JooqAttachmentMapper {
    fun toRecord(request: AttachmentCreateRequest): AttachmentsRecord

    @EnumMapping(nameTransformationStrategy = "case", configuration = "lower")
    fun mapEntityTypeToRecord(entityType: EntityType): com.gmotors.infra.jooq.enums.EntityType

    @EnumMapping(nameTransformationStrategy = "case", configuration = "lower")
    fun mapAttachmentTypeToRecord(type: AttachmentType): com.gmotors.infra.jooq.enums.AttachmentType
}