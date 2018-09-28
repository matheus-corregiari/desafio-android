package br.com.concrete.desafio.data.model

import com.google.gson.annotations.Expose

data class Page<T>(
    @Expose val totalCount: Long,
    @Expose val incompleteResults: Boolean,
    @Expose val items: List<T> = emptyList(),
    var nextPage: Int? = null
)