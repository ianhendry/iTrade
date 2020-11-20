package com.gracefl.tradeplus.service;

import com.gracefl.tradeplus.domain.TradeSignals;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TradeSignals}.
 */
public interface TradeSignalsService {

    /**
     * Save a tradeSignals.
     *
     * @param tradeSignals the entity to save.
     * @return the persisted entity.
     */
    TradeSignals save(TradeSignals tradeSignals);

    /**
     * Get all the tradeSignals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TradeSignals> findAll(Pageable pageable);


    /**
     * Get the "id" tradeSignals.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TradeSignals> findOne(Long id);

    /**
     * Delete the "id" tradeSignals.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
