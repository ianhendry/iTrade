package com.gracefl.tradeplus.service;

import com.gracefl.tradeplus.domain.DailyAnalysisPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DailyAnalysisPost}.
 */
public interface DailyAnalysisPostService {

    /**
     * Save a dailyAnalysisPost.
     *
     * @param dailyAnalysisPost the entity to save.
     * @return the persisted entity.
     */
    DailyAnalysisPost save(DailyAnalysisPost dailyAnalysisPost);

    /**
     * Get all the dailyAnalysisPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DailyAnalysisPost> findAll(Pageable pageable);


    /**
     * Get the "id" dailyAnalysisPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DailyAnalysisPost> findOne(Long id);

    /**
     * Delete the "id" dailyAnalysisPost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
