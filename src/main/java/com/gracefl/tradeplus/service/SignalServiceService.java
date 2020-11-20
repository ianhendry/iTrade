package com.gracefl.tradeplus.service;

import com.gracefl.tradeplus.domain.SignalService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SignalService}.
 */
public interface SignalServiceService {

    /**
     * Save a signalService.
     *
     * @param signalService the entity to save.
     * @return the persisted entity.
     */
    SignalService save(SignalService signalService);

    /**
     * Get all the signalServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SignalService> findAll(Pageable pageable);

    /**
     * Get all the signalServices with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<SignalService> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" signalService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SignalService> findOne(Long id);

    /**
     * Delete the "id" signalService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
