package com.gmotors.core.customers

interface CustomerRepository {
    fun create(request: CustomerCreateRequest)
}