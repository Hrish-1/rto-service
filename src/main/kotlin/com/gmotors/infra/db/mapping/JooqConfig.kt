package com.gmotors.infra.db.mapping

import org.mapstruct.InjectionStrategy
import org.mapstruct.MapperConfig
import org.mapstruct.ReportingPolicy

/**
 * Configuration for jooq mappers. Use @Mapper props to override anything here.
 */
@MapperConfig(
    componentModel = "spring", // generate spring @Component for DI
    injectionStrategy =  InjectionStrategy.CONSTRUCTOR, // use ctor injection like our own code
    unmappedTargetPolicy = ReportingPolicy.IGNORE // jooq records will have many unmapped props
)
interface JooqConfig