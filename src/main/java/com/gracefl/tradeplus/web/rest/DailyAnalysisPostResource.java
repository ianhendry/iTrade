package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.domain.DailyAnalysisPost;
import com.gracefl.tradeplus.service.DailyAnalysisPostService;
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
 * REST controller for managing {@link com.gracefl.tradeplus.domain.DailyAnalysisPost}.
 */
@RestController
@RequestMapping("/api")
public class DailyAnalysisPostResource {

    private final Logger log = LoggerFactory.getLogger(DailyAnalysisPostResource.class);

    private static final String ENTITY_NAME = "dailyAnalysisPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DailyAnalysisPostService dailyAnalysisPostService;

    public DailyAnalysisPostResource(DailyAnalysisPostService dailyAnalysisPostService) {
        this.dailyAnalysisPostService = dailyAnalysisPostService;
    }

    /**
     * {@code POST  /daily-analysis-posts} : Create a new dailyAnalysisPost.
     *
     * @param dailyAnalysisPost the dailyAnalysisPost to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dailyAnalysisPost, or with status {@code 400 (Bad Request)} if the dailyAnalysisPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/daily-analysis-posts")
    public ResponseEntity<DailyAnalysisPost> createDailyAnalysisPost(@Valid @RequestBody DailyAnalysisPost dailyAnalysisPost) throws URISyntaxException {
        log.debug("REST request to save DailyAnalysisPost : {}", dailyAnalysisPost);
        if (dailyAnalysisPost.getId() != null) {
            throw new BadRequestAlertException("A new dailyAnalysisPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DailyAnalysisPost result = dailyAnalysisPostService.save(dailyAnalysisPost);
        return ResponseEntity.created(new URI("/api/daily-analysis-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /daily-analysis-posts} : Updates an existing dailyAnalysisPost.
     *
     * @param dailyAnalysisPost the dailyAnalysisPost to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyAnalysisPost,
     * or with status {@code 400 (Bad Request)} if the dailyAnalysisPost is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dailyAnalysisPost couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/daily-analysis-posts")
    public ResponseEntity<DailyAnalysisPost> updateDailyAnalysisPost(@Valid @RequestBody DailyAnalysisPost dailyAnalysisPost) throws URISyntaxException {
        log.debug("REST request to update DailyAnalysisPost : {}", dailyAnalysisPost);
        if (dailyAnalysisPost.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DailyAnalysisPost result = dailyAnalysisPostService.save(dailyAnalysisPost);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyAnalysisPost.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /daily-analysis-posts} : get all the dailyAnalysisPosts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dailyAnalysisPosts in body.
     */
    @GetMapping("/daily-analysis-posts")
    public ResponseEntity<List<DailyAnalysisPost>> getAllDailyAnalysisPosts(Pageable pageable) {
        log.debug("REST request to get a page of DailyAnalysisPosts");
        Page<DailyAnalysisPost> page = dailyAnalysisPostService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /daily-analysis-posts/:id} : get the "id" dailyAnalysisPost.
     *
     * @param id the id of the dailyAnalysisPost to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dailyAnalysisPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/daily-analysis-posts/{id}")
    public ResponseEntity<DailyAnalysisPost> getDailyAnalysisPost(@PathVariable Long id) {
        log.debug("REST request to get DailyAnalysisPost : {}", id);
        Optional<DailyAnalysisPost> dailyAnalysisPost = dailyAnalysisPostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dailyAnalysisPost);
    }

    /**
     * {@code DELETE  /daily-analysis-posts/:id} : delete the "id" dailyAnalysisPost.
     *
     * @param id the id of the dailyAnalysisPost to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/daily-analysis-posts/{id}")
    public ResponseEntity<Void> deleteDailyAnalysisPost(@PathVariable Long id) {
        log.debug("REST request to delete DailyAnalysisPost : {}", id);
        dailyAnalysisPostService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
