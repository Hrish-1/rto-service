package com.gmotors.core.customers

import java.util.*

data class CustomerCreateRequest(
    val id: UUID,
    val name: String,
    val company: String? = null,
    val address: String? = null,
    val city: String? = null,
    val pinCode: String? = null,
    val state: String? = null,
    val stateCode: String? = null,
    val contact1: String? = null,
    val contact2: String? = null,
    val emailId: String? = null,
    val enteredBy: UUID,
)