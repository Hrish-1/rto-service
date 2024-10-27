package com.gmotors.core

@JvmInline
value class HtmlContent(private val s: String) {
    fun value(): String {
        return s
    }
}