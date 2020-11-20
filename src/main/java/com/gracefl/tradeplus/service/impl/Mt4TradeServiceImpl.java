package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.Mt4TradeService;
import com.gracefl.tradeplus.domain.Mt4Trade;
import com.gracefl.tradeplus.repository.Mt4TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Mt4Trade}.
 */
@Service
@Transactional
public class Mt4TradeServiceImpl implements Mt4TradeService {

    private final Logger log = LoggerFactory.getLogger(Mt4TradeServiceImpl.class);

    private final Mt4TradeRepository mt4TradeRepository;

    public Mt4TradeServiceImpl(Mt4TradeRepository mt4TradeRepository) {
        this.mt4TradeRepository = mt4TradeRepository;
    }

    @Override
    public Mt4Trade save(Mt4Trade mt4Trade) {
        log.debug("Request to save Mt4Trade : {}", mt4Trade);
        return mt4TradeRepository.save(mt4Trade);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Mt4Trade> findAll(Pageable pageable) {
        log.debug("Request to get all Mt4Trades");
        return mt4TradeRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Mt4Trade> findOne(Long id) {
        log.debug("Request to get Mt4Trade : {}", id);
        return mt4TradeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mt4Trade : {}", id);
        mt4TradeRepository.deleteById(id);
    }
}
