package com.gmotors.infra.db.mapping

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gmotors.core.transactions.*
import com.gmotors.infra.jooq.enums.TransactionStatus
import com.gmotors.infra.jooq.tables.records.TransactionsRecord
import org.jooq.JSONB
import org.jooq.JSONB.jsonb
import org.mapstruct.EnumMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(config = JooqConfig::class)
interface JooqTransactionMapper {

    @Mapping(source = "record.seller", target = "seller", qualifiedByName = ["mapJsonToSeller"])
    @Mapping(source = "record.purchaser", target = "purchaser", qualifiedByName = ["mapJsonToPurchaser"])
    fun toModel(record: TransactionsRecord): Transaction

    @Mapping(source = "request.seller", target = "seller", qualifiedByName = ["mapSellerToJson"])
    @Mapping(source = "request.purchaser", target = "purchaser", qualifiedByName = ["mapPurchaserToJson"])
    fun toRecord(request: TransactionCreateRequest, amount: Double): TransactionsRecord

    @Mapping(source = "request.seller", target = "seller", qualifiedByName = ["mapSellerToJson"])
    @Mapping(source = "request.purchaser", target = "purchaser", qualifiedByName = ["mapPurchaserToJson"])
    fun toRecord(request: TransactionUpdateRequest, amount: Double): TransactionsRecord

    @EnumMapping(nameTransformationStrategy = "case", configuration = "lower")
    fun mapStatusToRecord(status: Status): TransactionStatus

    @EnumMapping(nameTransformationStrategy = "case", configuration = "upper")
    fun mapStatusToModel(status: TransactionStatus): Status

    @Named("mapSellerToJson")
    fun mapSellerToJson(seller: Seller?): JSONB? = seller?.let {
        objectMapper().writeValueAsString(it).let(::jsonb)
    }

    @Named("mapPurchaserToJson")
    fun mapPurchaserToJson(purchaser: Purchaser?): JSONB? = purchaser?.let {
        objectMapper().writeValueAsString(it).let(::jsonb)
    }

    @Named("mapJsonToSeller")
    fun mapJsonToSeller(json: JSONB?): Seller? = json?.let {
        objectMapper().readValue<Seller>(json.data())
    }

    @Named("mapJsonToPurchaser")
    fun mapJsonToPurchaser(json: JSONB?): Purchaser? = json?.let {
        objectMapper().readValue<Purchaser>(json.data())
    }

    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerKotlinModule()
    }
}