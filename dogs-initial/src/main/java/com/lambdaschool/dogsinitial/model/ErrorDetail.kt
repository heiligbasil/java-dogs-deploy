package com.lambdaschool.dogsinitial.model

import com.lambdaschool.dogsinitial.exception.ValidationError
import java.text.SimpleDateFormat
import java.util.*

data class ErrorDetail(
        var title: String? = null,
        var status: Int = 0,
        var detail: String? = null,
        var timestamp: String? = null,
        var developerMessage: String? = null,
        var errors: MutableMap<String, MutableList<ValidationError>> = HashMap<String, MutableList<ValidationError>>()
)
{
    fun setTimestamp(timestamp: Long?)
    {
        this.timestamp = SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(Date(timestamp!!))
    }
}

