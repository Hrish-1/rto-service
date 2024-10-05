package com.gmotors.core.transactions

import com.gmotors.infra.jooq.enums.TransactionStatus

data class TransactionQuery(
    val page: Int = 0,
    val size: Int = 10,
    val status: TransactionStatus?,
)
