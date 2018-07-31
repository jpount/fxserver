package com.excelian.fxserver.service;

import com.excelian.fxserver.domain.Currency;
import com.excelian.fxserver.repository.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Currency.
 */
@Service
@Transactional
public class CurrencyService {

    protected final CurrencyRepository currencyRepository;
    private final Logger log = LoggerFactory.getLogger(CurrencyService.class);

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * Save a currency.
     *
     * @param currency the entity to save
     * @return the persisted entity
     */
    public Currency save(Currency currency) {
        log.debug("Request to save Currency : {}", currency);
        return currencyRepository.save(currency);
    }

    /**
     * Get all the currencies.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Currency> findAll() {
        log.debug("Request to get all Currencies");
        return currencyRepository.findAll();
    }


    /**
     * Get one currency by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Currency> findOne(Long id) {
        log.debug("Request to get Currency : {}", id);
        return currencyRepository.findById(id);
    }

    /**
     * Delete the currency by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Currency : {}", id);
        currencyRepository.deleteById(id);
    }
}
