package com.gmotors.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.gmotors")
@ConfigurationPropertiesScan("com.gmotors")
class RtoApplication

fun main(args: Array<String>) {
    runApplication<RtoApplication>(*args)
}
