package com.gracefl.tradeplus.service;

import com.gracefl.tradeplus.domain.Mt4Trade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Mt4Trade}.
 */
public interface Mt4TradeService {

    /**
     * Save a mt4Trade.
     *
     * @param mt4Trade the entity to save.
     * @return the persisted entity.
     */
    Mt4Trade save(Mt4Trade mt4Trade);

    /**
     * Get all the mt4Trades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Mt4Trade> findAll(Pageable pageable);


    /**
     * Get the "id" mt4Trade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Mt4Trade> findOne(Long id);

    /**
     * Delete the "id" mt4Trade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
