package com.gracefl.tradeplus.service;

import com.gracefl.tradeplus.domain.ShippingDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ShippingDetails}.
 */
public interface ShippingDetailsService {

    /**
     * Save a shippingDetails.
     *
     * @param shippingDetails the entity to save.
     * @return the persisted entity.
     */
    ShippingDetails save(ShippingDetails shippingDetails);

    /**
     * Get all the shippingDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShippingDetails> findAll(Pageable pageable);


    /**
     * Get the "id" shippingDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShippingDetails> findOne(Long id);

    /**
     * Delete the "id" shippingDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
