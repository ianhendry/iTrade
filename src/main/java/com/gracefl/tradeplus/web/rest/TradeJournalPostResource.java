package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.domain.TradeJournalPost;
import com.gracefl.tradeplus.service.TradeJournalPostService;
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
 * REST controller for managing {@link com.gracefl.tradeplus.domain.TradeJournalPost}.
 */
@RestController
@RequestMapping("/api")
public class TradeJournalPostResource {

    private final Logger log = LoggerFactory.getLogger(TradeJournalPostResource.class);

    private static final String ENTITY_NAME = "tradeJournalPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TradeJournalPostService tradeJournalPostService;

    public TradeJournalPostResource(TradeJournalPostService tradeJournalPostService) {
        this.tradeJournalPostService = tradeJournalPostService;
    }

    /**
     * {@code POST  /trade-journal-posts} : Create a new tradeJournalPost.
     *
     * @param tradeJournalPost the tradeJournalPost to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tradeJournalPost, or with status {@code 400 (Bad Request)} if the tradeJournalPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trade-journal-posts")
    public ResponseEntity<TradeJournalPost> createTradeJournalPost(@Valid @RequestBody TradeJournalPost tradeJournalPost) throws URISyntaxException {
        log.debug("REST request to save TradeJournalPost : {}", tradeJournalPost);
        if (tradeJournalPost.getId() != null) {
            throw new BadRequestAlertException("A new tradeJournalPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TradeJournalPost result = tradeJournalPostService.save(tradeJournalPost);
        return ResponseEntity.created(new URI("/api/trade-journal-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trade-journal-posts} : Updates an existing tradeJournalPost.
     *
     * @param tradeJournalPost the tradeJournalPost to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tradeJournalPost,
     * or with status {@code 400 (Bad Request)} if the tradeJournalPost is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tradeJournalPost couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trade-journal-posts")
    public ResponseEntity<TradeJournalPost> updateTradeJournalPost(@Valid @RequestBody TradeJournalPost tradeJournalPost) throws URISyntaxException {
        log.debug("REST request to update TradeJournalPost : {}", tradeJournalPost);
        if (tradeJournalPost.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TradeJournalPost result = tradeJournalPostService.save(tradeJournalPost);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tradeJournalPost.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trade-journal-posts} : get all the tradeJournalPosts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tradeJournalPosts in body.
     */
    @GetMapping("/trade-journal-posts")
    public ResponseEntity<List<TradeJournalPost>> getAllTradeJournalPosts(Pageable pageable) {
        log.debug("REST request to get a page of TradeJournalPosts");
        Page<TradeJournalPost> page = tradeJournalPostService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trade-journal-posts/:id} : get the "id" tradeJournalPost.
     *
     * @param id the id of the tradeJournalPost to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tradeJournalPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trade-journal-posts/{id}")
    public ResponseEntity<TradeJournalPost> getTradeJournalPost(@PathVariable Long id) {
        log.debug("REST request to get TradeJournalPost : {}", id);
        Optional<TradeJournalPost> tradeJournalPost = tradeJournalPostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tradeJournalPost);
    }

    /**
     * {@code DELETE  /trade-journal-posts/:id} : delete the "id" tradeJournalPost.
     *
     * @param id the id of the tradeJournalPost to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trade-journal-posts/{id}")
    public ResponseEntity<Void> deleteTradeJournalPost(@PathVariable Long id) {
        log.debug("REST request to delete TradeJournalPost : {}", id);
        tradeJournalPostService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
