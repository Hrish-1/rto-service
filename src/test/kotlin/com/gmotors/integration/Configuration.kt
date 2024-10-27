package com.gmotors.integration

import com.gmotors.core.FileSystem
import com.gmotors.fakes.FakeFileSystem
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class Configuration {

    @Bean
    @Primary
    fun fileSystem(): FileSystem {
        println("Initializing fake file system")
        return FakeFileSystem()
    }
}