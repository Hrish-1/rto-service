package com.gmotors.app.v1.dtos

import com.gmotors.core.transactions.Status

data class TransactionQueryDto(
    val page: Int = 0,
    val size: Int = 10,
    val status: Status?,
)
