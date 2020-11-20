package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.PriceDataHistoryService;
import com.gracefl.tradeplus.domain.PriceDataHistory;
import com.gracefl.tradeplus.repository.PriceDataHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PriceDataHistory}.
 */
@Service
@Transactional
public class PriceDataHistoryServiceImpl implements PriceDataHistoryService {

    private final Logger log = LoggerFactory.getLogger(PriceDataHistoryServiceImpl.class);

    private final PriceDataHistoryRepository priceDataHistoryRepository;

    public PriceDataHistoryServiceImpl(PriceDataHistoryRepository priceDataHistoryRepository) {
        this.priceDataHistoryRepository = priceDataHistoryRepository;
    }

    @Override
    public PriceDataHistory save(PriceDataHistory priceDataHistory) {
        log.debug("Request to save PriceDataHistory : {}", priceDataHistory);
        return priceDataHistoryRepository.save(priceDataHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PriceDataHistory> findAll(Pageable pageable) {
        log.debug("Request to get all PriceDataHistories");
        return priceDataHistoryRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PriceDataHistory> findOne(Long id) {
        log.debug("Request to get PriceDataHistory : {}", id);
        return priceDataHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriceDataHistory : {}", id);
        priceDataHistoryRepository.deleteById(id);
    }
}
