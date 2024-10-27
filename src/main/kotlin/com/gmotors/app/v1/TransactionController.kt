package com.gmotors.app.v1

import com.gmotors.app.v1.dtos.TransactionCreateRequestDto
import com.gmotors.app.v1.dtos.TransactionDto
import com.gmotors.app.v1.dtos.TransactionQueryDto
import com.gmotors.app.v1.dtos.TransactionUpdateRequestDto
import com.gmotors.app.v1.mapping.TransactionMapper
import com.gmotors.core.PagedItems
import com.gmotors.core.transactions.TransactionService
import org.springdoc.api.annotations.ParameterObject
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/v1/transactions")
class TransactionController(
    private val service: TransactionService,
    private val mapper: TransactionMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody requestDto: TransactionCreateRequestDto,
        @RequestParam("userId") userId: UUID // TODO: read from jwt token
    ): Map<String, UUID> {
        val request = mapper.toRequestModel(requestDto, userId)
        service.create(request)
        return mapOf("id" to request.id)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun list(@ParameterObject query: TransactionQueryDto): PagedItems<TransactionDto> =
        service
            .list(mapper.toQuery(query))
            .mapItems(mapper::toDto)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: UUID) {
        service.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable("id") id: UUID, @RequestBody requestDto: TransactionUpdateRequestDto) {
        service.update(mapper.toRequestModel(requestDto, id))
    }

}