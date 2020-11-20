package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.TradeSignalsService;
import com.gracefl.tradeplus.domain.TradeSignals;
import com.gracefl.tradeplus.repository.TradeSignalsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TradeSignals}.
 */
@Service
@Transactional
public class TradeSignalsServiceImpl implements TradeSignalsService {

    private final Logger log = LoggerFactory.getLogger(TradeSignalsServiceImpl.class);

    private final TradeSignalsRepository tradeSignalsRepository;

    public TradeSignalsServiceImpl(TradeSignalsRepository tradeSignalsRepository) {
        this.tradeSignalsRepository = tradeSignalsRepository;
    }

    @Override
    public TradeSignals save(TradeSignals tradeSignals) {
        log.debug("Request to save TradeSignals : {}", tradeSignals);
        return tradeSignalsRepository.save(tradeSignals);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TradeSignals> findAll(Pageable pageable) {
        log.debug("Request to get all TradeSignals");
        return tradeSignalsRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TradeSignals> findOne(Long id) {
        log.debug("Request to get TradeSignals : {}", id);
        return tradeSignalsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TradeSignals : {}", id);
        tradeSignalsRepository.deleteById(id);
    }
}
