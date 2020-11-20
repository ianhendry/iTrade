package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.domain.SignalService;
import com.gracefl.tradeplus.service.SignalServiceService;
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
 * REST controller for managing {@link com.gracefl.tradeplus.domain.SignalService}.
 */
@RestController
@RequestMapping("/api")
public class SignalServiceResource {

    private final Logger log = LoggerFactory.getLogger(SignalServiceResource.class);

    private static final String ENTITY_NAME = "signalService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SignalServiceService signalServiceService;

    public SignalServiceResource(SignalServiceService signalServiceService) {
        this.signalServiceService = signalServiceService;
    }

    /**
     * {@code POST  /signal-services} : Create a new signalService.
     *
     * @param signalService the signalService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new signalService, or with status {@code 400 (Bad Request)} if the signalService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/signal-services")
    public ResponseEntity<SignalService> createSignalService(@Valid @RequestBody SignalService signalService) throws URISyntaxException {
        log.debug("REST request to save SignalService : {}", signalService);
        if (signalService.getId() != null) {
            throw new BadRequestAlertException("A new signalService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SignalService result = signalServiceService.save(signalService);
        return ResponseEntity.created(new URI("/api/signal-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /signal-services} : Updates an existing signalService.
     *
     * @param signalService the signalService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signalService,
     * or with status {@code 400 (Bad Request)} if the signalService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the signalService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/signal-services")
    public ResponseEntity<SignalService> updateSignalService(@Valid @RequestBody SignalService signalService) throws URISyntaxException {
        log.debug("REST request to update SignalService : {}", signalService);
        if (signalService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SignalService result = signalServiceService.save(signalService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, signalService.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /signal-services} : get all the signalServices.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of signalServices in body.
     */
    @GetMapping("/signal-services")
    public ResponseEntity<List<SignalService>> getAllSignalServices(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of SignalServices");
        Page<SignalService> page;
        if (eagerload) {
            page = signalServiceService.findAllWithEagerRelationships(pageable);
        } else {
            page = signalServiceService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /signal-services/:id} : get the "id" signalService.
     *
     * @param id the id of the signalService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the signalService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/signal-services/{id}")
    public ResponseEntity<SignalService> getSignalService(@PathVariable Long id) {
        log.debug("REST request to get SignalService : {}", id);
        Optional<SignalService> signalService = signalServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(signalService);
    }

    /**
     * {@code DELETE  /signal-services/:id} : delete the "id" signalService.
     *
     * @param id the id of the signalService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/signal-services/{id}")
    public ResponseEntity<Void> deleteSignalService(@PathVariable Long id) {
        log.debug("REST request to delete SignalService : {}", id);
        signalServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
