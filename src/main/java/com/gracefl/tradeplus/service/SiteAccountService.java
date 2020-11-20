package com.gracefl.tradeplus.service;

import com.gracefl.tradeplus.domain.SiteAccount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SiteAccount}.
 */
public interface SiteAccountService {

    /**
     * Save a siteAccount.
     *
     * @param siteAccount the entity to save.
     * @return the persisted entity.
     */
    SiteAccount save(SiteAccount siteAccount);

    /**
     * Get all the siteAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SiteAccount> findAll(Pageable pageable);


    /**
     * Get the "id" siteAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SiteAccount> findOne(Long id);

    /**
     * Delete the "id" siteAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
