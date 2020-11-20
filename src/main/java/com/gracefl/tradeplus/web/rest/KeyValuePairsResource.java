package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.domain.KeyValuePairs;
import com.gracefl.tradeplus.service.KeyValuePairsService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gracefl.tradeplus.domain.KeyValuePairs}.
 */
@RestController
@RequestMapping("/api")
public class KeyValuePairsResource {

    private final Logger log = LoggerFactory.getLogger(KeyValuePairsResource.class);

    private static final String ENTITY_NAME = "keyValuePairs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeyValuePairsService keyValuePairsService;

    public KeyValuePairsResource(KeyValuePairsService keyValuePairsService) {
        this.keyValuePairsService = keyValuePairsService;
    }

    /**
     * {@code POST  /key-value-pairs} : Create a new keyValuePairs.
     *
     * @param keyValuePairs the keyValuePairs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keyValuePairs, or with status {@code 400 (Bad Request)} if the keyValuePairs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/key-value-pairs")
    public ResponseEntity<KeyValuePairs> createKeyValuePairs(@RequestBody KeyValuePairs keyValuePairs) throws URISyntaxException {
        log.debug("REST request to save KeyValuePairs : {}", keyValuePairs);
        if (keyValuePairs.getId() != null) {
            throw new BadRequestAlertException("A new keyValuePairs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeyValuePairs result = keyValuePairsService.save(keyValuePairs);
        return ResponseEntity.created(new URI("/api/key-value-pairs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /key-value-pairs} : Updates an existing keyValuePairs.
     *
     * @param keyValuePairs the keyValuePairs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keyValuePairs,
     * or with status {@code 400 (Bad Request)} if the keyValuePairs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keyValuePairs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/key-value-pairs")
    public ResponseEntity<KeyValuePairs> updateKeyValuePairs(@RequestBody KeyValuePairs keyValuePairs) throws URISyntaxException {
        log.debug("REST request to update KeyValuePairs : {}", keyValuePairs);
        if (keyValuePairs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KeyValuePairs result = keyValuePairsService.save(keyValuePairs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, keyValuePairs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /key-value-pairs} : get all the keyValuePairs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keyValuePairs in body.
     */
    @GetMapping("/key-value-pairs")
    public ResponseEntity<List<KeyValuePairs>> getAllKeyValuePairs(Pageable pageable) {
        log.debug("REST request to get a page of KeyValuePairs");
        Page<KeyValuePairs> page = keyValuePairsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /key-value-pairs/:id} : get the "id" keyValuePairs.
     *
     * @param id the id of the keyValuePairs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keyValuePairs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/key-value-pairs/{id}")
    public ResponseEntity<KeyValuePairs> getKeyValuePairs(@PathVariable Long id) {
        log.debug("REST request to get KeyValuePairs : {}", id);
        Optional<KeyValuePairs> keyValuePairs = keyValuePairsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(keyValuePairs);
    }

    /**
     * {@code DELETE  /key-value-pairs/:id} : delete the "id" keyValuePairs.
     *
     * @param id the id of the keyValuePairs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/key-value-pairs/{id}")
    public ResponseEntity<Void> deleteKeyValuePairs(@PathVariable Long id) {
        log.debug("REST request to delete KeyValuePairs : {}", id);
        keyValuePairsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
