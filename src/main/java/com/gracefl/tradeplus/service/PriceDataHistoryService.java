package com.gracefl.tradeplus.service;

import com.gracefl.tradeplus.domain.PriceDataHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link PriceDataHistory}.
 */
public interface PriceDataHistoryService {

    /**
     * Save a priceDataHistory.
     *
     * @param priceDataHistory the entity to save.
     * @return the persisted entity.
     */
    PriceDataHistory save(PriceDataHistory priceDataHistory);

    /**
     * Get all the priceDataHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PriceDataHistory> findAll(Pageable pageable);


    /**
     * Get the "id" priceDataHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PriceDataHistory> findOne(Long id);

    /**
     * Delete the "id" priceDataHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
