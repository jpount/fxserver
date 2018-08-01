package com.excelian.fxserver.service

import com.excelian.fxserver.domain.Currency
import com.excelian.fxserver.repository.CurrencyRepository
import com.excelian.fxserver.util.validateNotNull
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
class ForexService(
    private val currencyRepository: CurrencyRepository
) {

    companion object {
        val DEFAULT_ROUNDING = MathContext(5, RoundingMode.HALF_EVEN)
    }

    /**
     * Implements Convert API.
     */
    @Transactional(readOnly = true)
    fun convert(from: String, to: String, amount: BigDecimal): BigDecimal {
        val currencies = currencyRepository.findAll()
        val conversionLookup = currencies.associateBy { it.symbol }

        val fromCurrency = conversionLookup[from]
        validateNotNull(fromCurrency) { "'From' argument currency symbol is not recognized" }

        val toCurrency = conversionLookup[to]
        validateNotNull(toCurrency) { "'To' argument currency symbol is not recognized" }

        return convert(fromCurrency!!, toCurrency!!, amount)
    }

    /**
     * Implements Convert API.
     */
    @Transactional(readOnly = true)
    fun convert(from: Currency, to: Currency, amount: BigDecimal): BigDecimal =
        to.rate.multiply(amount).div(from.rate).round(DEFAULT_ROUNDING)

    /**
     * Implements Latest rates API.
     */
    @Transactional(readOnly = true)
    fun latest(symbols: List<*>?): Map<String, BigDecimal> {
        val filterLookup = symbols?.map(Any?::toString)?.toHashSet() ?: emptySet<String>()
        val currencies = currencyRepository.findAll()

        val filtered = if (filterLookup.isEmpty()) currencies else currencies.filter { it.symbol in filterLookup }
        return filtered.map { it.symbol to it.rate }.toMap()
    }

    /**
     * Implements Symbols API.
     */
    @Transactional(readOnly = true)
    fun symbols(): Map<String, String> {
        val currencies = currencyRepository.findAll()
        return currencies.map { it.symbol to it.name }.toMap()
    }

}
