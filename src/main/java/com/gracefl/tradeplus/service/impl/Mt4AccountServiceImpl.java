package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.Mt4AccountService;
import com.gracefl.tradeplus.domain.Mt4Account;
import com.gracefl.tradeplus.repository.Mt4AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Mt4Account}.
 */
@Service
@Transactional
public class Mt4AccountServiceImpl implements Mt4AccountService {

    private final Logger log = LoggerFactory.getLogger(Mt4AccountServiceImpl.class);

    private final Mt4AccountRepository mt4AccountRepository;

    public Mt4AccountServiceImpl(Mt4AccountRepository mt4AccountRepository) {
        this.mt4AccountRepository = mt4AccountRepository;
    }

    @Override
    public Mt4Account save(Mt4Account mt4Account) {
        log.debug("Request to save Mt4Account : {}", mt4Account);
        return mt4AccountRepository.save(mt4Account);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Mt4Account> findAll(Pageable pageable) {
        log.debug("Request to get all Mt4Accounts");
        return mt4AccountRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Mt4Account> findOne(Long id) {
        log.debug("Request to get Mt4Account : {}", id);
        return mt4AccountRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mt4Account : {}", id);
        mt4AccountRepository.deleteById(id);
    }
}
