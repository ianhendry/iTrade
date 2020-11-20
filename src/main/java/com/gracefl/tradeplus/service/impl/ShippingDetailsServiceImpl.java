package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.ShippingDetailsService;
import com.gracefl.tradeplus.domain.ShippingDetails;
import com.gracefl.tradeplus.repository.ShippingDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ShippingDetails}.
 */
@Service
@Transactional
public class ShippingDetailsServiceImpl implements ShippingDetailsService {

    private final Logger log = LoggerFactory.getLogger(ShippingDetailsServiceImpl.class);

    private final ShippingDetailsRepository shippingDetailsRepository;

    public ShippingDetailsServiceImpl(ShippingDetailsRepository shippingDetailsRepository) {
        this.shippingDetailsRepository = shippingDetailsRepository;
    }

    @Override
    public ShippingDetails save(ShippingDetails shippingDetails) {
        log.debug("Request to save ShippingDetails : {}", shippingDetails);
        return shippingDetailsRepository.save(shippingDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShippingDetails> findAll(Pageable pageable) {
        log.debug("Request to get all ShippingDetails");
        return shippingDetailsRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ShippingDetails> findOne(Long id) {
        log.debug("Request to get ShippingDetails : {}", id);
        return shippingDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShippingDetails : {}", id);
        shippingDetailsRepository.deleteById(id);
    }
}
