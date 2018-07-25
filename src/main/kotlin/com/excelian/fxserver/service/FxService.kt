package com.excelian.fxserver.service

import com.excelian.fxserver.repository.CurrencyRepository
import com.excelian.fxserver.web.rest.v1.model.ConversionResult
import com.excelian.fxserver.web.rest.v1.model.MapValueResult
import com.google.common.base.Preconditions
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode


/**
 * Provides FX API implementation
 *
 * Created by dtsimbal on 7/24/18.
 */
@Service
@Transactional
class FxService(
    private val currencyRepository: CurrencyRepository
) {

    companion object {
        val DEFAULT_ROUNDING = MathContext(5, RoundingMode.HALF_EVEN)
    }

    /**
     * Implements Convert API.
     */
    @Transactional(readOnly = true)
    fun convert(from: String?, to: String?, amount: BigDecimal?): ConversionResult {
        val currencies = currencyRepository.findAll()
        val conversionLookup = currencies.map { it.symbol to it.rate }.toMap()

        Preconditions.checkArgument(conversionLookup[from] != null) { "'From' argument currency symbol is not recognized" }
        Preconditions.checkArgument(conversionLookup[to] != null) { "'To' argument currency symbol is not recognized" }

        val rateFrom = conversionLookup[from]!!
        val rateTo = conversionLookup[to]!!

        val value = rateTo.multiply(amount).div(rateFrom)
            .round(DEFAULT_ROUNDING)

        return ConversionResult(true, value)
    }

    /**
     * Implements Latest rates API.
     */
    @Transactional(readOnly = true)
    fun latest(symbols: List<*>?): MapValueResult<String, BigDecimal> {
        val filterLookup = symbols?.map(Any?::toString)?.toHashSet() ?: emptySet<String>()
        val currencies = currencyRepository.findAll()

        val filtered = if (filterLookup.isEmpty()) currencies else currencies.filter { it.symbol in filterLookup }
        val value = filtered.map { it.symbol to it.rate }.toMap()

        return MapValueResult(true, value)
    }

    /**
     * Implements Symbols API.
     */
    @Transactional(readOnly = true)
    fun symbols(): MapValueResult<String, String> {
        val currencies = currencyRepository.findAll()
        val value = currencies.map { it.symbol to it.name }.toMap()

        return MapValueResult(true, value)
    }

}
