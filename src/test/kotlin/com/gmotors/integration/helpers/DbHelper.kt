package com.gmotors.integration.helpers

import com.gmotors.core.customers.CustomerCreateRequest
import com.gmotors.core.customers.CustomerRepository
import com.gmotors.core.employees.Employee
import com.gmotors.core.employees.EmployeeCreateRequest
import com.gmotors.core.employees.EmployeeRepository
import com.gmotors.core.servicetransaction.ServiceTransactionRepository
import com.gmotors.core.transactions.Status
import com.gmotors.core.transactions.Transaction
import com.gmotors.core.transactions.TransactionCreateRequest
import com.gmotors.core.transactions.TransactionRepository
import com.gmotors.infra.jooq.tables.records.ServicesRecord
import com.gmotors.infra.jooq.tables.references.CUSTOMERS
import com.gmotors.infra.jooq.tables.references.EMPLOYEES
import com.gmotors.infra.jooq.tables.references.SERVICES
import io.github.serpro69.kfaker.Faker
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import java.util.*

@TestComponent
class DbHelper @Autowired constructor(
    private val dsl: DSLContext,
    private val txRepo: TransactionRepository,
    private val customerRepo: CustomerRepository,
    private val employeeRepo: EmployeeRepository,
    private val svcTxnRepo: ServiceTransactionRepository
) {

    fun createEmployee(): UUID {
        val faker = Faker()
        val request = EmployeeCreateRequest(
            id = UUID.randomUUID(),
            email = faker.internet.email(),
            userName = faker.name.name().take(10),
            password = "qwerty",
            status = Employee.Status.ACTIVE,
        )
        employeeRepo.create(request)
        return request.id
    }

    fun createCustomer(employeeId: UUID): UUID {
        val request = CustomerCreateRequest(
            id = UUID.randomUUID(),
            name = "",
            enteredBy = employeeId
        )
        customerRepo.create(request)
        return request.id
    }

    fun createTransactions(n: Int): List<UUID> {
        return (1..n).toList().map { _ -> createTransaction() }
    }

    fun createTransaction(): UUID {
        val employeeId = createEmployee()
        val request = TransactionCreateRequest(
            id = UUID.randomUUID(),
            status = Status.READY,
            vehicleNumber = "XYZ",
            fromRto = "",
            toRto = "",
            createdBy = employeeId,
            customerId = createCustomer(employeeId)
        )
        txRepo.create(request)
        return request.id
    }

    fun getTransaction(id: UUID): Transaction? {
        return txRepo.get(id)
    }

    fun getServiceIdsByTransactionId(id: UUID): List<UUID> {
        return svcTxnRepo.findByTransactionId(id)
            .map { x -> x.serviceId }
    }

    fun employeeCount(): Int = dsl.fetchCount(EMPLOYEES)

    fun customerCount(): Int = dsl.fetchCount(CUSTOMERS)

    fun getServices() = dsl.fetch(SERVICES).toList()

    fun createServicesIfNotExists(): List<UUID> {
        val services = getServices()
        if (services.isNotEmpty()) {
            return services.map { x -> x.id!! }
        }
        val svc1 = createServiceRecord("Terminator 1", "T1", 100.0)
        val svc2 = createServiceRecord("Terminator 2", "T2", 200.0)
        val svc3 = createServiceRecord("Terminator 3", "T3", 300.0)
        val svc4 = createServiceRecord("Terminator 4", "T4", 400.0)
        dsl.batchInsert(svc1, svc2, svc3, svc4).execute()
        return listOf(svc1.id!!, svc2.id!!, svc3.id!!, svc4.id!!)
    }

    fun createServiceRecord(name: String, shortName: String, amount: Double): ServicesRecord {
        return dsl.newRecord(SERVICES).apply {
            set(SERVICES.ID, UUID.randomUUID())
            set(SERVICES.NAME, name)
            set(SERVICES.SHORT_NAME, shortName)
            set(SERVICES.AMOUNT, amount)
        }
    }
}