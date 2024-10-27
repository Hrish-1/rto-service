package com.gmotors.core.services

import java.util.UUID

interface ServiceRepository {
    fun totalAmount(ids: Set<UUID>): Double
}