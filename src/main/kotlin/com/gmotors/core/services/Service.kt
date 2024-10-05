package com.gmotors.core.services

import java.util.UUID;

data class Service(
    val id: UUID,
    val name: String,
    val shortName: String,
    val amount: Double
)