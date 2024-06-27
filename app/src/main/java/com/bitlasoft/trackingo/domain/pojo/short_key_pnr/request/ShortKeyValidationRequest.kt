package com.bitlasoft.trackingo.domain.pojo.short_key_pnr.request

data class ShortKeyValidationRequest(
    val short_key: String,
    val pnr_number: String
)