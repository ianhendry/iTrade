package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.domain.SiteAccount;
import com.gracefl.tradeplus.service.SiteAccountService;
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
 * REST controller for managing {@link com.gracefl.tradeplus.domain.SiteAccount}.
 */
@RestController
@RequestMapping("/api")
public class SiteAccountResource {

    private final Logger log = LoggerFactory.getLogger(SiteAccountResource.class);

    private static final String ENTITY_NAME = "siteAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteAccountService siteAccountService;

    public SiteAccountResource(SiteAccountService siteAccountService) {
        this.siteAccountService = siteAccountService;
    }

    /**
     * {@code POST  /site-accounts} : Create a new siteAccount.
     *
     * @param siteAccount the siteAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new siteAccount, or with status {@code 400 (Bad Request)} if the siteAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/site-accounts")
    public ResponseEntity<SiteAccount> createSiteAccount(@RequestBody SiteAccount siteAccount) throws URISyntaxException {
        log.debug("REST request to save SiteAccount : {}", siteAccount);
        if (siteAccount.getId() != null) {
            throw new BadRequestAlertException("A new siteAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiteAccount result = siteAccountService.save(siteAccount);
        return ResponseEntity.created(new URI("/api/site-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /site-accounts} : Updates an existing siteAccount.
     *
     * @param siteAccount the siteAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteAccount,
     * or with status {@code 400 (Bad Request)} if the siteAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siteAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/site-accounts")
    public ResponseEntity<SiteAccount> updateSiteAccount(@RequestBody SiteAccount siteAccount) throws URISyntaxException {
        log.debug("REST request to update SiteAccount : {}", siteAccount);
        if (siteAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SiteAccount result = siteAccountService.save(siteAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /site-accounts} : get all the siteAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siteAccounts in body.
     */
    @GetMapping("/site-accounts")
    public ResponseEntity<List<SiteAccount>> getAllSiteAccounts(Pageable pageable) {
        log.debug("REST request to get a page of SiteAccounts");
        Page<SiteAccount> page = siteAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /site-accounts/:id} : get the "id" siteAccount.
     *
     * @param id the id of the siteAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the siteAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/site-accounts/{id}")
    public ResponseEntity<SiteAccount> getSiteAccount(@PathVariable Long id) {
        log.debug("REST request to get SiteAccount : {}", id);
        Optional<SiteAccount> siteAccount = siteAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siteAccount);
    }

    /**
     * {@code DELETE  /site-accounts/:id} : delete the "id" siteAccount.
     *
     * @param id the id of the siteAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/site-accounts/{id}")
    public ResponseEntity<Void> deleteSiteAccount(@PathVariable Long id) {
        log.debug("REST request to delete SiteAccount : {}", id);
        siteAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
