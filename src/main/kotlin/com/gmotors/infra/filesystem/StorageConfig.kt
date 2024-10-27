package com.gmotors.infra.filesystem

import org.springframework.boot.context.properties.ConfigurationProperties
import java.nio.file.Path
import java.nio.file.Paths

@ConfigurationProperties(prefix = "rto.storage")
data class StorageConfig(
    val root: String,
) {
    init {
        require(root.trim().isNotEmpty()) { "File upload location can not be Empty." }
    }

    fun getRootPath(): Path {
        return Paths.get(root)
    }
}
