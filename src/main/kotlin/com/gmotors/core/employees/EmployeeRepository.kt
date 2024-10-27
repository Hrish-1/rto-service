package com.gmotors.core.employees

interface EmployeeRepository {
    fun create(request: EmployeeCreateRequest)
}