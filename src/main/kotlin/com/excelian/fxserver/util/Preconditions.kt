package com.excelian.fxserver.util

import com.excelian.fxserver.web.rest.errors.InvalidArgumentException

/**
 * Created by dtsimbal on 8/1/18.
 */
fun <T> validateNotNull(value: T, messageBuilder: () -> String): T {
    if (value == null) {
        throw InvalidArgumentException(messageBuilder.invoke())
    }
    return value
}


fun validate(condition: Boolean, messageBuilder: () -> String) {
    if (!condition) {
        throw InvalidArgumentException(messageBuilder.invoke())
    }
}
