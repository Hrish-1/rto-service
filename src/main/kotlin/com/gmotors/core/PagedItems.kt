package com.gmotors.core

data class PagedItems<T>(
    val items: List<T>,
    val page: Int,
    val size: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val totalPages: Int,
    val totalItems: Int,
) {
    fun <U> mapItems(mapper: (T) -> U): PagedItems<U> {
        return PagedItems(
            items.map(mapper),
            page,
            size,
            isFirst,
            isLast,
            totalPages,
            totalItems,
        )
    }
}
