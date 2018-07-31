package com.excelian.fxserver.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.excelian.fxserver.domain.CurrencyRate;
import com.excelian.fxserver.service.CurrencyRateService;
import com.excelian.fxserver.web.rest.errors.BadRequestAlertException;
import com.excelian.fxserver.web.rest.util.HeaderUtil;
import com.excelian.fxserver.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CurrencyRate.
 */
@RestController
@RequestMapping("/api")
public class CurrencyRateResource {

    private static final String ENTITY_NAME = "currencyRate";
    private final Logger log = LoggerFactory.getLogger(CurrencyRateResource.class);
    private final CurrencyRateService currencyRateService;

    public CurrencyRateResource(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    /**
     * POST  /currency-rates : Create a new currencyRate.
     *
     * @param currencyRate the currencyRate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new currencyRate, or with status 400 (Bad Request) if the currencyRate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/currency-rates")
    @Timed
    public ResponseEntity<CurrencyRate> createCurrencyRate(@Valid @RequestBody CurrencyRate currencyRate) throws URISyntaxException {
        log.debug("REST request to save CurrencyRate : {}", currencyRate);
        if (currencyRate.getId() != null) {
            throw new BadRequestAlertException("A new currencyRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrencyRate result = currencyRateService.save(currencyRate);
        return ResponseEntity.created(new URI("/api/currency-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /currency-rates : Updates an existing currencyRate.
     *
     * @param currencyRate the currencyRate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated currencyRate,
     * or with status 400 (Bad Request) if the currencyRate is not valid,
     * or with status 500 (Internal Server Error) if the currencyRate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/currency-rates")
    @Timed
    public ResponseEntity<CurrencyRate> updateCurrencyRate(@Valid @RequestBody CurrencyRate currencyRate) throws URISyntaxException {
        log.debug("REST request to update CurrencyRate : {}", currencyRate);
        if (currencyRate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurrencyRate result = currencyRateService.save(currencyRate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, currencyRate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /currency-rates : get all the currencyRates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of currencyRates in body
     */
    @GetMapping("/currency-rates")
    @Timed
    public ResponseEntity<List<CurrencyRate>> getAllCurrencyRates(Pageable pageable) {
        log.debug("REST request to get a page of CurrencyRates");
        Page<CurrencyRate> page = currencyRateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/currency-rates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /currency-rates/:id : get the "id" currencyRate.
     *
     * @param id the id of the currencyRate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the currencyRate, or with status 404 (Not Found)
     */
    @GetMapping("/currency-rates/{id}")
    @Timed
    public ResponseEntity<CurrencyRate> getCurrencyRate(@PathVariable Long id) {
        log.debug("REST request to get CurrencyRate : {}", id);
        Optional<CurrencyRate> currencyRate = currencyRateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(currencyRate);
    }

    /**
     * DELETE  /currency-rates/:id : delete the "id" currencyRate.
     *
     * @param id the id of the currencyRate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/currency-rates/{id}")
    @Timed
    public ResponseEntity<Void> deleteCurrencyRate(@PathVariable Long id) {
        log.debug("REST request to delete CurrencyRate : {}", id);
        currencyRateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
