package com.gracefl.tradeplus.service;

import com.gracefl.tradeplus.domain.KeyValuePairs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link KeyValuePairs}.
 */
public interface KeyValuePairsService {

    /**
     * Save a keyValuePairs.
     *
     * @param keyValuePairs the entity to save.
     * @return the persisted entity.
     */
    KeyValuePairs save(KeyValuePairs keyValuePairs);

    /**
     * Get all the keyValuePairs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KeyValuePairs> findAll(Pageable pageable);


    /**
     * Get the "id" keyValuePairs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KeyValuePairs> findOne(Long id);

    /**
     * Delete the "id" keyValuePairs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
