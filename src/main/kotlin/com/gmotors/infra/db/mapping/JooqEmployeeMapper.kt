package com.gmotors.infra.db.mapping

import com.gmotors.core.employees.Employee
import com.gmotors.core.employees.EmployeeCreateRequest
import com.gmotors.infra.jooq.enums.EmployeeStatus
import com.gmotors.infra.jooq.enums.Gender
import com.gmotors.infra.jooq.tables.records.EmployeesRecord
import org.mapstruct.EnumMapping
import org.mapstruct.Mapper

@Mapper(config = JooqConfig::class)
interface JooqEmployeeMapper {
    fun toRecord(request: EmployeeCreateRequest): EmployeesRecord

    @EnumMapping(nameTransformationStrategy = "case", configuration = "lower")
    fun mapGenderToRecord(gender: Employee.Gender): Gender

    @EnumMapping(nameTransformationStrategy = "case", configuration = "lower")
    fun mapStatusToRecord(staus: Employee.Status): EmployeeStatus
}