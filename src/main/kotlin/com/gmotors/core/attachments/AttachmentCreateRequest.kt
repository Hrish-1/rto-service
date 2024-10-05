package com.gmotors.core.attachments

import java.util.*

data class AttachmentCreateRequest(
    val id: UUID?,
    val type: AttachmentType,
    val entityType: EntityType,
    val entityId: UUID,
    val contentType: String?,
    val ext: String?
)
