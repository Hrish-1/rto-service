package com.gmotors.integration.api

import com.gmotors.integration.BaseAppIntegrationTest
import io.restassured.RestAssured.given
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.specification.RequestSpecification
import org.springframework.boot.test.web.server.LocalServerPort

abstract class BaseApiIntegrationTests : BaseAppIntegrationTest() {

    @LocalServerPort
    val port: Int = 0

    abstract val basePath: String

    protected fun restAssured(): RequestSpecification {
        return restAssured(basePath)
    }

    protected fun restAssured(basePath: String?): RequestSpecification {
        val logConfig = LogConfig
            .logConfig()
            .enableLoggingOfRequestAndResponseIfValidationFails()
            .enablePrettyPrinting(true)
        return given()
            .baseUri("http://localhost")
            .port(port)
            .basePath(basePath)
            .config(RestAssuredConfig().logConfig(logConfig))
    }
}