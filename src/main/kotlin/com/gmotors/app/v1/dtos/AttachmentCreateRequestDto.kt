package com.gmotors.app.v1.dtos

import com.gmotors.core.attachments.EntityType
import org.springframework.web.multipart.MultipartFile
import java.util.*

data class AttachmentCreateRequestDto(
    val entityType: EntityType,
    val entityId: UUID,
    val aadhaar: MultipartFile?,
    val pan: MultipartFile?,
    val insurance: MultipartFile?,
    val chassis: MultipartFile?,
    val createForms: Boolean
)
