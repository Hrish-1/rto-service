package com.gmotors.app.v1.dtos

import com.gmotors.core.attachments.EntityType
import java.util.UUID

data class AttachmentPartialCreateRequestDto(
    val entityType: EntityType,
    val entityId: UUID,
    val createForms: Boolean = false
)
