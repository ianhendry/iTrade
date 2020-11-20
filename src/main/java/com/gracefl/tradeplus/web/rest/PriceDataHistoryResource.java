package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.domain.PriceDataHistory;
import com.gracefl.tradeplus.service.PriceDataHistoryService;
import com.gracefl.tradeplus.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gracefl.tradeplus.domain.PriceDataHistory}.
 */
@RestController
@RequestMapping("/api")
public class PriceDataHistoryResource {

    private final Logger log = LoggerFactory.getLogger(PriceDataHistoryResource.class);

    private static final String ENTITY_NAME = "priceDataHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PriceDataHistoryService priceDataHistoryService;

    public PriceDataHistoryResource(PriceDataHistoryService priceDataHistoryService) {
        this.priceDataHistoryService = priceDataHistoryService;
    }

    /**
     * {@code POST  /price-data-histories} : Create a new priceDataHistory.
     *
     * @param priceDataHistory the priceDataHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new priceDataHistory, or with status {@code 400 (Bad Request)} if the priceDataHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/price-data-histories")
    public ResponseEntity<PriceDataHistory> createPriceDataHistory(@Valid @RequestBody PriceDataHistory priceDataHistory) throws URISyntaxException {
        log.debug("REST request to save PriceDataHistory : {}", priceDataHistory);
        if (priceDataHistory.getId() != null) {
            throw new BadRequestAlertException("A new priceDataHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriceDataHistory result = priceDataHistoryService.save(priceDataHistory);
        return ResponseEntity.created(new URI("/api/price-data-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /price-data-histories} : Updates an existing priceDataHistory.
     *
     * @param priceDataHistory the priceDataHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priceDataHistory,
     * or with status {@code 400 (Bad Request)} if the priceDataHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the priceDataHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/price-data-histories")
    public ResponseEntity<PriceDataHistory> updatePriceDataHistory(@Valid @RequestBody PriceDataHistory priceDataHistory) throws URISyntaxException {
        log.debug("REST request to update PriceDataHistory : {}", priceDataHistory);
        if (priceDataHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PriceDataHistory result = priceDataHistoryService.save(priceDataHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, priceDataHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /price-data-histories} : get all the priceDataHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of priceDataHistories in body.
     */
    @GetMapping("/price-data-histories")
    public ResponseEntity<List<PriceDataHistory>> getAllPriceDataHistories(Pageable pageable) {
        log.debug("REST request to get a page of PriceDataHistories");
        Page<PriceDataHistory> page = priceDataHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /price-data-histories/:id} : get the "id" priceDataHistory.
     *
     * @param id the id of the priceDataHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the priceDataHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/price-data-histories/{id}")
    public ResponseEntity<PriceDataHistory> getPriceDataHistory(@PathVariable Long id) {
        log.debug("REST request to get PriceDataHistory : {}", id);
        Optional<PriceDataHistory> priceDataHistory = priceDataHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priceDataHistory);
    }

    /**
     * {@code DELETE  /price-data-histories/:id} : delete the "id" priceDataHistory.
     *
     * @param id the id of the priceDataHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/price-data-histories/{id}")
    public ResponseEntity<Void> deletePriceDataHistory(@PathVariable Long id) {
        log.debug("REST request to delete PriceDataHistory : {}", id);
        priceDataHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
