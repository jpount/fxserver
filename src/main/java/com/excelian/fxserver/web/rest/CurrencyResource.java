package com.excelian.fxserver.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.excelian.fxserver.domain.Currency;
import com.excelian.fxserver.service.CurrencyService;
import com.excelian.fxserver.web.rest.errors.BadRequestAlertException;
import com.excelian.fxserver.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Currency.
 */
@RestController
@RequestMapping("/api")
public class CurrencyResource {

    private final Logger log = LoggerFactory.getLogger(CurrencyResource.class);

    private static final String ENTITY_NAME = "currency";

    private final CurrencyService currencyService;

    public CurrencyResource(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * POST  /currencies : Create a new currency.
     *
     * @param currency the currency to create
     * @return the ResponseEntity with status 201 (Created) and with body the new currency, or with status 400 (Bad Request) if the currency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/currencies")
    @Timed
    public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency) throws URISyntaxException {
        log.debug("REST request to save Currency : {}", currency);
        if (currency.getId() != null) {
            throw new BadRequestAlertException("A new currency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Currency result = currencyService.save(currency);
        return ResponseEntity.created(new URI("/api/currencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /currencies : Updates an existing currency.
     *
     * @param currency the currency to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated currency,
     * or with status 400 (Bad Request) if the currency is not valid,
     * or with status 500 (Internal Server Error) if the currency couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/currencies")
    @Timed
    public ResponseEntity<Currency> updateCurrency(@RequestBody Currency currency) throws URISyntaxException {
        log.debug("REST request to update Currency : {}", currency);
        if (currency.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Currency result = currencyService.save(currency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, currency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /currencies : get all the currencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of currencies in body
     */
    @GetMapping("/currencies")
    @Timed
    public List<Currency> getAllCurrencies() {
        log.debug("REST request to get all Currencies");
        return currencyService.findAll();
    }

    /**
     * GET  /currencies/:id : get the "id" currency.
     *
     * @param id the id of the currency to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the currency, or with status 404 (Not Found)
     */
    @GetMapping("/currencies/{id}")
    @Timed
    public ResponseEntity<Currency> getCurrency(@PathVariable Long id) {
        log.debug("REST request to get Currency : {}", id);
        Optional<Currency> currency = currencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(currency);
    }

    /**
     * DELETE  /currencies/:id : delete the "id" currency.
     *
     * @param id the id of the currency to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/currencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        log.debug("REST request to delete Currency : {}", id);
        currencyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
