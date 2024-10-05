package com.gmotors.core.employees

import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.util.*

data class Employee (
    val id: UUID,
    val userName: String,
    val email: String,
    val password: String,
    val status: Status,
    val level: Level,
    val firstName: String?,
    val middleName: String?,
    val lastName: String?,
    val gender: Gender?,
    val birthDate: LocalDate?,
    val mobileNo: String?,
    val address: String?,
    val aadharNo: String?,
    val pan: String?,
    val joiningDate: LocalDate?,
    val endDate: LocalDate = LocalDate.of(9999, 12, 31),
) {
    enum class Gender { MALE, FEMALE, OTHERS }
    enum class Status { ACTIVE, INACTIVE }
    enum class Level(val value: Int) { ONE(1), TWO(2), THREE(3) }
}