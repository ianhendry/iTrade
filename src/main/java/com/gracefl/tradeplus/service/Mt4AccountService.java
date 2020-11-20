package com.gracefl.tradeplus.service;

import com.gracefl.tradeplus.domain.Mt4Account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Mt4Account}.
 */
public interface Mt4AccountService {

    /**
     * Save a mt4Account.
     *
     * @param mt4Account the entity to save.
     * @return the persisted entity.
     */
    Mt4Account save(Mt4Account mt4Account);

    /**
     * Get all the mt4Accounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Mt4Account> findAll(Pageable pageable);


    /**
     * Get the "id" mt4Account.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Mt4Account> findOne(Long id);

    /**
     * Delete the "id" mt4Account.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
