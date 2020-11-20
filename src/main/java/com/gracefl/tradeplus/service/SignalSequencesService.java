package com.gracefl.tradeplus.service;

import com.gracefl.tradeplus.domain.SignalSequences;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SignalSequences}.
 */
public interface SignalSequencesService {

    /**
     * Save a signalSequences.
     *
     * @param signalSequences the entity to save.
     * @return the persisted entity.
     */
    SignalSequences save(SignalSequences signalSequences);

    /**
     * Get all the signalSequences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SignalSequences> findAll(Pageable pageable);


    /**
     * Get the "id" signalSequences.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SignalSequences> findOne(Long id);

    /**
     * Delete the "id" signalSequences.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
