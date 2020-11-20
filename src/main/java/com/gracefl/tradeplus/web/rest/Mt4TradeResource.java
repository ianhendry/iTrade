package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.domain.Mt4Trade;
import com.gracefl.tradeplus.service.Mt4TradeService;
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
 * REST controller for managing {@link com.gracefl.tradeplus.domain.Mt4Trade}.
 */
@RestController
@RequestMapping("/api")
public class Mt4TradeResource {

    private final Logger log = LoggerFactory.getLogger(Mt4TradeResource.class);

    private static final String ENTITY_NAME = "mt4Trade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Mt4TradeService mt4TradeService;

    public Mt4TradeResource(Mt4TradeService mt4TradeService) {
        this.mt4TradeService = mt4TradeService;
    }

    /**
     * {@code POST  /mt-4-trades} : Create a new mt4Trade.
     *
     * @param mt4Trade the mt4Trade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mt4Trade, or with status {@code 400 (Bad Request)} if the mt4Trade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mt-4-trades")
    public ResponseEntity<Mt4Trade> createMt4Trade(@Valid @RequestBody Mt4Trade mt4Trade) throws URISyntaxException {
        log.debug("REST request to save Mt4Trade : {}", mt4Trade);
        if (mt4Trade.getId() != null) {
            throw new BadRequestAlertException("A new mt4Trade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mt4Trade result = mt4TradeService.save(mt4Trade);
        return ResponseEntity.created(new URI("/api/mt-4-trades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mt-4-trades} : Updates an existing mt4Trade.
     *
     * @param mt4Trade the mt4Trade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mt4Trade,
     * or with status {@code 400 (Bad Request)} if the mt4Trade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mt4Trade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mt-4-trades")
    public ResponseEntity<Mt4Trade> updateMt4Trade(@Valid @RequestBody Mt4Trade mt4Trade) throws URISyntaxException {
        log.debug("REST request to update Mt4Trade : {}", mt4Trade);
        if (mt4Trade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mt4Trade result = mt4TradeService.save(mt4Trade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mt4Trade.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mt-4-trades} : get all the mt4Trades.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mt4Trades in body.
     */
    @GetMapping("/mt-4-trades")
    public ResponseEntity<List<Mt4Trade>> getAllMt4Trades(Pageable pageable) {
        log.debug("REST request to get a page of Mt4Trades");
        Page<Mt4Trade> page = mt4TradeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mt-4-trades/:id} : get the "id" mt4Trade.
     *
     * @param id the id of the mt4Trade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mt4Trade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mt-4-trades/{id}")
    public ResponseEntity<Mt4Trade> getMt4Trade(@PathVariable Long id) {
        log.debug("REST request to get Mt4Trade : {}", id);
        Optional<Mt4Trade> mt4Trade = mt4TradeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mt4Trade);
    }

    /**
     * {@code DELETE  /mt-4-trades/:id} : delete the "id" mt4Trade.
     *
     * @param id the id of the mt4Trade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mt-4-trades/{id}")
    public ResponseEntity<Void> deleteMt4Trade(@PathVariable Long id) {
        log.debug("REST request to delete Mt4Trade : {}", id);
        mt4TradeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
