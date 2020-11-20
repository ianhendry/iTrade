package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.domain.TradeSignals;
import com.gracefl.tradeplus.service.TradeSignalsService;
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
 * REST controller for managing {@link com.gracefl.tradeplus.domain.TradeSignals}.
 */
@RestController
@RequestMapping("/api")
public class TradeSignalsResource {

    private final Logger log = LoggerFactory.getLogger(TradeSignalsResource.class);

    private static final String ENTITY_NAME = "tradeSignals";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TradeSignalsService tradeSignalsService;

    public TradeSignalsResource(TradeSignalsService tradeSignalsService) {
        this.tradeSignalsService = tradeSignalsService;
    }

    /**
     * {@code POST  /trade-signals} : Create a new tradeSignals.
     *
     * @param tradeSignals the tradeSignals to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tradeSignals, or with status {@code 400 (Bad Request)} if the tradeSignals has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trade-signals")
    public ResponseEntity<TradeSignals> createTradeSignals(@Valid @RequestBody TradeSignals tradeSignals) throws URISyntaxException {
        log.debug("REST request to save TradeSignals : {}", tradeSignals);
        if (tradeSignals.getId() != null) {
            throw new BadRequestAlertException("A new tradeSignals cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TradeSignals result = tradeSignalsService.save(tradeSignals);
        return ResponseEntity.created(new URI("/api/trade-signals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trade-signals} : Updates an existing tradeSignals.
     *
     * @param tradeSignals the tradeSignals to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tradeSignals,
     * or with status {@code 400 (Bad Request)} if the tradeSignals is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tradeSignals couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trade-signals")
    public ResponseEntity<TradeSignals> updateTradeSignals(@Valid @RequestBody TradeSignals tradeSignals) throws URISyntaxException {
        log.debug("REST request to update TradeSignals : {}", tradeSignals);
        if (tradeSignals.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TradeSignals result = tradeSignalsService.save(tradeSignals);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tradeSignals.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trade-signals} : get all the tradeSignals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tradeSignals in body.
     */
    @GetMapping("/trade-signals")
    public ResponseEntity<List<TradeSignals>> getAllTradeSignals(Pageable pageable) {
        log.debug("REST request to get a page of TradeSignals");
        Page<TradeSignals> page = tradeSignalsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trade-signals/:id} : get the "id" tradeSignals.
     *
     * @param id the id of the tradeSignals to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tradeSignals, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trade-signals/{id}")
    public ResponseEntity<TradeSignals> getTradeSignals(@PathVariable Long id) {
        log.debug("REST request to get TradeSignals : {}", id);
        Optional<TradeSignals> tradeSignals = tradeSignalsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tradeSignals);
    }

    /**
     * {@code DELETE  /trade-signals/:id} : delete the "id" tradeSignals.
     *
     * @param id the id of the tradeSignals to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trade-signals/{id}")
    public ResponseEntity<Void> deleteTradeSignals(@PathVariable Long id) {
        log.debug("REST request to delete TradeSignals : {}", id);
        tradeSignalsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
