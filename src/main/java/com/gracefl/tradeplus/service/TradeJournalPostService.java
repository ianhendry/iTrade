package com.gracefl.tradeplus.service;

import com.gracefl.tradeplus.domain.TradeJournalPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TradeJournalPost}.
 */
public interface TradeJournalPostService {

    /**
     * Save a tradeJournalPost.
     *
     * @param tradeJournalPost the entity to save.
     * @return the persisted entity.
     */
    TradeJournalPost save(TradeJournalPost tradeJournalPost);

    /**
     * Get all the tradeJournalPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TradeJournalPost> findAll(Pageable pageable);


    /**
     * Get the "id" tradeJournalPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TradeJournalPost> findOne(Long id);

    /**
     * Delete the "id" tradeJournalPost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
