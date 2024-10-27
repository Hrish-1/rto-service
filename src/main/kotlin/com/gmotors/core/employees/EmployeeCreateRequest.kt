package com.gmotors.core.employees

import com.gmotors.core.employees.Employee.*
import java.time.LocalDate
import java.util.*

data class EmployeeCreateRequest(
    val id: UUID,
    val userName: String,
    val email: String,
    val password: String,
    val status: Status,
    val firstName: String? = null,
    val middleName: String? = null,
    val lastName: String? = null,
    val gender: Gender? = null,
    val birthDate: LocalDate? = null,
    val mobileNo: String? = null,
    val address: String? = null,
    val aadharNo: String? = null,
    val pan: String? = null,
)