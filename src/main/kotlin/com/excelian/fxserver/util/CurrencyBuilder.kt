package com.excelian.fxserver.util

import com.excelian.fxserver.domain.Currency
import java.math.BigDecimal

/**
 * Builds Currency entity
 *
 * Created by dtsimbal on 7/25/18.
 */
class CurrencyBuilder(
    val currency: Currency = Currency()
) {

    fun symbol(value: String): CurrencyBuilder {
        currency.symbol = value
        return this
    }


    fun name(value: String): CurrencyBuilder {
        currency.name = value
        return this
    }

    fun rate(value: BigDecimal): CurrencyBuilder {
        currency.rate = value
        return this
    }

    fun build(): Currency = currency

}
