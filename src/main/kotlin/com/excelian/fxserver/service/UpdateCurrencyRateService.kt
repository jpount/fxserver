package com.excelian.fxserver.service

import com.excelian.fxserver.domain.Currency
import com.excelian.fxserver.domain.CurrencyRate
import com.excelian.fxserver.repository.CurrencyRateRepository
import com.excelian.fxserver.repository.CurrencyRepository
import com.excelian.fxserver.util.CurrencyBuilder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import javax.transaction.Transactional

/**
 * Updates currency rates and symbols in the system
 *
 * Created by dtsimbal on 7/24/18.
 */
@Service
@Transactional
class UpdateCurrencyRateService(
    val currencyRepository: CurrencyRepository,
    val currencyRateRepository: CurrencyRateRepository,
    val fixerClientService: FixerClientService
) {

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(UpdateCurrencyRateService::class.java)
    }

    fun updateCurrencySymbols() {
        log.info("Updating currency symbols")
        fixerClientService.fetchSymbols()
            .thenApply { updateCurrencySymbolsWith(it.symbols) }
            .exceptionally { log.error("Fail to fetch currency symbols ", it) }
    }

    fun updateCurrencyRates() {
        log.info("Updating currency rates")
        fixerClientService.fetchLatestRates()
            .thenApply { updateCurrencyRatesWith(it.rates) }
            .exceptionally { log.error("Fail to fetch latest currency rates", it) }
    }

    fun updateCurrencyRatesWith(rates: Map<String, BigDecimal>) {
        val currencies = currencyRepository.findAll()
        val currencyRates = mutableListOf<CurrencyRate>()
        // Fetches all existing currencies
        val lookup = currencies.map { it.symbol to it }.toMap()
        // Updates a rate
        rates.forEach {
            val currency = lookup[it.key]
            if (currency != null) {
                currency.rate(it.value)
                currencyRates += CurrencyRate().currency(currency).rate(it.value)
            }
        }

        currencyRepository.saveAll(currencies)
        currencyRateRepository.saveAll(currencyRates)
    }

    fun updateCurrencySymbolsWith(symbols: Map<String, String>) {
        val currencies = currencyRepository.findAll()
        // Fetches all existing currencies
        val existing = currencies.map { it.symbol to it }.toMap<String, Currency>()

        // Collects new currencies to add
        val entitiesToAdd = HashMap<String, String>(symbols)
        entitiesToAdd.keys.removeAll(existing.keys)

        // Collects currencies to delete
        val entitiesToDelete = HashMap<String, Currency>(existing)
        entitiesToDelete.keys.removeAll(symbols.keys)

        currencyRepository.saveAll(entitiesToAdd.map {
            CurrencyBuilder().symbol(it.key).name(it.value)
                .rate(BigDecimal(1.0)).build()
        })

        currencyRepository.deleteAll(entitiesToDelete.values)
    }

}

