package com.excelian.fxserver.service;

import com.excelian.fxserver.domain.CurrencyRate;
import com.excelian.fxserver.repository.CurrencyRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CurrencyRate.
 */
@Service
@Transactional
public class CurrencyRateService {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateService.class);

    private final CurrencyRateRepository currencyRateRepository;

    public CurrencyRateService(CurrencyRateRepository currencyRateRepository) {
        this.currencyRateRepository = currencyRateRepository;
    }

    /**
     * Save a currencyRate.
     *
     * @param currencyRate the entity to save
     * @return the persisted entity
     */
    public CurrencyRate save(CurrencyRate currencyRate) {
        log.debug("Request to save CurrencyRate : {}", currencyRate);
        return currencyRateRepository.save(currencyRate);
    }

    /**
     * Get all the currencyRates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CurrencyRate> findAll(Pageable pageable) {
        log.debug("Request to get all CurrencyRates");
        return currencyRateRepository.findAll(pageable);
    }


    /**
     * Get one currencyRate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CurrencyRate> findOne(Long id) {
        log.debug("Request to get CurrencyRate : {}", id);
        return currencyRateRepository.findById(id);
    }

    /**
     * Delete the currencyRate by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CurrencyRate : {}", id);
        currencyRateRepository.deleteById(id);
    }
}
