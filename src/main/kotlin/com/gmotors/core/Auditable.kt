package com.gmotors.core

import java.time.LocalDateTime
import java.time.OffsetDateTime

interface Auditable {
    val createdAt: OffsetDateTime
    val updatedAt: OffsetDateTime?
}