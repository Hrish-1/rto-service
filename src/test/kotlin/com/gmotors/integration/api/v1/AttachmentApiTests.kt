package com.gmotors.integration.api.v1

import com.gmotors.integration.api.BaseApiIntegrationTests
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.util.ResourceUtils.getFile
import java.util.UUID

class AttachmentApiTests : BaseApiIntegrationTests() {

    override val basePath: String = "/v1/attachments"

    @Test
    fun `create should work`() {
        val txId = dbHelper.createTransaction()
        restAssured()
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("aadhaar", getFile("./src/test/resources/test.txt"), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("request", getAttachmentJson(txId), MediaType.APPLICATION_JSON_VALUE)
            .post()
            .then()
            .statusCode(201)
    }

    fun getAttachmentJson(txId: UUID): Map<String, Any> {
        return mapOf(
            "entityType" to "TRANSACTION",
            "entityId" to txId,
            "createForms" to true
        )
    }
}