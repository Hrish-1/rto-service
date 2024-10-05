package com.gmotors.infra.filesystem

import com.gmotors.core.attachments.AttachmentType
import com.samskivert.mustache.Mustache
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader
import org.springframework.stereotype.Service

@Service
class TemplateToHtml(
    private val loader: MustacheResourceTemplateLoader,
    private val compiler: Mustache.Compiler
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    fun convert(template: AttachmentType, data: Map<String, Any>): String {
        return compiler
            .compile(loader.getTemplate(template.name.lowercase()).readText())
            .execute(data)
    }
}