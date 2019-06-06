package com.lambdaschool.dogsinitial.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidDataTypeException : RuntimeException
{
    constructor(message: String) : super(message)
    {
    }

    constructor(message: String, cause: Throwable) : super(message, cause)
    {
    }

    companion object
    {
        private val serialVersionUID = 1L
    }
}
