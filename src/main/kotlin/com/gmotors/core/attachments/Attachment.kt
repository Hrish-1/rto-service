package com.gmotors.core.attachments

import java.util.UUID;

data class Attachment(
    val id: UUID,
    val type: AttachmentType,
    val entityType: EntityType,
    val contentType: String,
    val ext: String,
    val entityId: UUID
)

enum class EntityType {
    EMPLOYEE,
    TRANSACTION
}

enum class AttachmentType {
    AADHAAR,
    PAN,
    INSURANCE,
    CHASSIS,
    FORM30_1,
    FORM30_2,
    FORM29,
    PROFILE;

    fun templateName(): String {
        return this.name.lowercase()
    }
}
