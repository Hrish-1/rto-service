package com.gmotors.integration.api.v1

import com.gmotors.core.transactions.Status
import com.gmotors.integration.api.BaseApiIntegrationTests
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import java.util.*

class TransactionApiTests : BaseApiIntegrationTests() {

    override val basePath: String = "/v1/transactions"

    @Test
    fun `pagination should work`() {
        dbHelper.createTransactions(4)

        // Page 1
        restAssured()
            .queryParams("page", 0, "size", 2)
            .get("")
            .then()
            .statusCode(200)
            .body("items.size()", `is`(2))
            .body("isFirst", `is`(true))
            .body("isLast", `is`(false))
            .body("totalPages", `is`(2))
            .body("totalItems", `is`(4))

        // Page 2
        restAssured()
            .queryParams("page", 1, "size", 2)
            .get()
            .then()
            .statusCode(200)
            .body("items.size()", `is`(2))
            .body("isFirst", `is`(false))
            .body("isLast", `is`(true))
            .body("totalPages", `is`(2))
            .body("totalItems", `is`(4))
    }

    @Test
    fun `create should work`() {
        val employeeId = dbHelper.createEmployee()
        val customerId = dbHelper.createCustomer(employeeId)
        val serviceIds = dbHelper.createServicesIfNotExists().take(2).toSet()
        val txId = UUID.fromString(restAssured()
            .queryParams("userId", employeeId)
            .body(getTransactionJson(customerId, employeeId, serviceIds))
            .contentType(ContentType.JSON)
            .post()
            .then()
            .statusCode(201)
            .extract().jsonPath().get("id"))
        val txn = requireNotNull(dbHelper.getTransaction(txId)) { "Transaction should be present" }
        assertThat(txn.amount).isEqualTo(300.0)
        val txnSvcIds = dbHelper.getServiceIdsByTransactionId(txId)
        assertThat(txnSvcIds).hasSameElementsAs(serviceIds)
    }

    fun getTransactionJson(customerId: UUID, employeeId: UUID, serviceIds: Set<UUID>): Map<String, Any> {
        return mapOf(
            "status" to Status.READY,
            "vehicleNumber" to "",
            "toRto" to "",
            "fromRto" to "",
            "customerId" to customerId,
            "serviceIds" to serviceIds
        )
    }
}