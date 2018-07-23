package com.excelian.fxserver.service

import com.excelian.fxserver.domain.Currency
import com.excelian.fxserver.repository.CurrencyRepository
import com.excelian.fxserver.web.api.ApiApiDelegate
import com.excelian.fxserver.web.api.model.ConversionResult
import com.excelian.fxserver.web.api.model.MapValueResult
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

/**
 * Created by dtsimbal on 7/23/18.
 */
@Service
@Transactional
class CurrencyService(
    private val currencyRepository: CurrencyRepository
) : ApiApiDelegate {

    private val log = LoggerFactory.getLogger(CurrencyService::class.java)

    /**
     * Save a currency.
     *
     * @param currency the entity to save
     * @return the persisted entity
     */
    fun save(currency: Currency): Currency {
        log.debug("Request to save Currency : {}", currency)
        return currencyRepository.save(currency)
    }

    /**
     * Get all the currencies.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    fun findAll(): List<Currency> {
        log.debug("Request to get all Currencies")
        return currencyRepository.findAll()
    }


    /**
     * Get one currency by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    fun findOne(id: Long?): Optional<Currency> {
        log.debug("Request to get Currency : {}", id)
        return currencyRepository.findById(id!!)
    }

    /**
     * Delete the currency by id.
     *
     * @param id the id of the entity
     */
    fun delete(id: Long?) {
        log.debug("Request to delete Currency : {}", id)
        currencyRepository.deleteById(id!!)
    }

    /**
     * Implements Convert API.
     */
    @Transactional(readOnly = true)
    override fun apiV1ConvertGet(from: String?, to: String?, amount: BigDecimal?): ResponseEntity<ConversionResult> {
        val currencies = currencyRepository.findAll()
        val conversionLookup = currencies.map { it.symbol to it.rate }.toMap()

        val rateFrom = conversionLookup[from]!!
        val rateTo = conversionLookup[to]!!
        val value = rateFrom.div(rateTo).multiply(amount)

        val result = ConversionResult().success(true).value(value)
        return ResponseEntity.ok(result)
    }

    /**
     * Implements Latest rates API.
     */
    @Transactional(readOnly = true)
    override fun apiV1LatestGet(symbols: List<Any?>?): ResponseEntity<MapValueResult> {
        val filterLookup = symbols?.map(Any?::toString)?.toHashSet() ?: emptySet<String>()
        val currencies = currencyRepository.findAll()

        val filtered = if (filterLookup.isEmpty()) currencies else currencies.filter { it.symbol in filterLookup }
        val value = filtered.map { it.symbol to it.rate }.toMap()

        val result = MapValueResult().success(true).value(value)
        return ResponseEntity.ok(result)
    }

    /**
     * Implements Symbols API.
     */
    @Transactional(readOnly = true)
    override fun apiV1SymbolsGet(): ResponseEntity<MapValueResult> {
        val currencies = currencyRepository.findAll()
        val value = currencies.map { it.symbol to it.name }.toMap()

        val result = MapValueResult().success(true).value(value)
        return ResponseEntity.ok(result)
    }

}
