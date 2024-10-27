package com.gmotors.integration

import com.gmotors.app.Application
import com.gmotors.integration.helpers.DbHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [Application::class, Configuration::class]
)
@ActiveProfiles(profiles = ["test"])
abstract class BaseAppIntegrationTest {

    @Autowired
    final lateinit var dbHelper: DbHelper

    companion object {
        @Container
        val container = PostgreSQLContainer("postgres:15.8-alpine").apply {
            withDatabaseName("rto")
            withUsername("postgres")
            withPassword("postgres")
            start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.username", container::getUsername)
            registry.add("spring.datasource.password", container::getPassword)
        }
    }

}