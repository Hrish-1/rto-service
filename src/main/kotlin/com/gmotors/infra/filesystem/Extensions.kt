package com.gmotors.infra.filesystem

import org.springframework.web.multipart.MultipartFile

/**
 * Returns file extensions (png, jpg, pdf etc.) based on the original fileName
 */
fun MultipartFile.ext(): String {
    return sanitize(this.originalFilename?.split(".")?.last()) ?: ""
}

private fun sanitize(value: String?) =
    value?.lowercase()
        ?.trim()
        ?.removePrefix(".")
        ?.replace(Regex("[^a-z0-9]"), "")
        ?.take(10)