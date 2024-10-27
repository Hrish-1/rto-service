package com.gmotors.core

interface HtmlGenerator {
    fun generate(templateName: String, data: Map<String, Any>): HtmlContent
}