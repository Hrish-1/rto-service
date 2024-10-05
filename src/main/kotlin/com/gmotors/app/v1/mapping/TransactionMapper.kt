package com.gmotors.app.v1.mapping

import com.gmotors.app.v1.dtos.TransactionCreateRequestDto
import com.gmotors.app.v1.dtos.TransactionDto
import com.gmotors.app.v1.dtos.TransactionQueryDto
import com.gmotors.app.v1.dtos.TransactionUpdateRequestDto
import com.gmotors.core.mapping.Config
import com.gmotors.core.transactions.Transaction
import com.gmotors.core.transactions.TransactionCreateRequest
import com.gmotors.core.transactions.TransactionQuery
import com.gmotors.core.transactions.TransactionUpdateRequest
import com.gmotors.infra.db.mapping.JooqTransactionMapper
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import java.util.*

@Mapper(config = Config::class, uses = [JooqTransactionMapper::class])
interface TransactionMapper {

    @Mapping(target = "id", expression = "java(randomUUID())")
    fun toRequestModel(request: TransactionCreateRequestDto, createdBy: UUID): TransactionCreateRequest

    fun toRequestModel(request: TransactionUpdateRequestDto, id: UUID): TransactionUpdateRequest

    fun toQuery(queryDto: TransactionQueryDto): TransactionQuery

    fun toDto(model: Transaction): TransactionDto

    fun randomUUID(): UUID {
        return UUID.randomUUID()
    }
}