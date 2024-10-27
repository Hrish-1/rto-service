package com.gmotors.infra.filesystem

import com.gmotors.core.HtmlContent
import com.gmotors.core.HtmlGenerator
import com.samskivert.mustache.Mustache
import org.springframework.stereotype.Service

@Service
class MustacheHtmlGenerator(private val compiler: Mustache.Compiler) : HtmlGenerator {
    override fun generate(templateName: String, data: Map<String, Any>): HtmlContent {
        return HtmlContent(compiler
            .loadTemplate(templateName)
            .execute(data))
    }
}