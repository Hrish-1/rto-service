package com.gmotors.core.attachments

import java.util.UUID

interface AttachmentRepository {
    fun create(request: AttachmentCreateRequest)
    fun listByTransaction(transactionId: UUID): Map<String, String>
}