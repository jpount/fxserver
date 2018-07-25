package com.excelian.fxserver.service

import com.excelian.fxserver.domain.Currency
import com.excelian.fxserver.repository.CurrencyRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * Provides CRUD operations over Currency entity
 *
 * Created by dtsimbal on 7/23/18.
 */
@Service
@Transactional
class CurrencyService(
    private val currencyRepository: CurrencyRepository
) {

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
     * Saves a batch of currencies
     *
     * @param currencies currency entities for update
     */
    fun saveAll(currencies: Iterable<Currency>): List<Currency> {
        log.debug("Request to save currencies : {}", currencies.map { it.symbol }.joinToString())
        return currencyRepository.saveAll(currencies)
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
     * Deletes all provided currencies
     *
     * @param currencies existing currency entities
     */
    fun deleteAll(currencies: Iterable<Currency>) {
        log.debug("Request to delete currencies : {}", currencies.joinToString())
        currencyRepository.deleteAll(currencies)
    }

}
