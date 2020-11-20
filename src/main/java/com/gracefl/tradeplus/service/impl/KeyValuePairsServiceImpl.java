package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.KeyValuePairsService;
import com.gracefl.tradeplus.domain.KeyValuePairs;
import com.gracefl.tradeplus.repository.KeyValuePairsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link KeyValuePairs}.
 */
@Service
@Transactional
public class KeyValuePairsServiceImpl implements KeyValuePairsService {

    private final Logger log = LoggerFactory.getLogger(KeyValuePairsServiceImpl.class);

    private final KeyValuePairsRepository keyValuePairsRepository;

    public KeyValuePairsServiceImpl(KeyValuePairsRepository keyValuePairsRepository) {
        this.keyValuePairsRepository = keyValuePairsRepository;
    }

    @Override
    public KeyValuePairs save(KeyValuePairs keyValuePairs) {
        log.debug("Request to save KeyValuePairs : {}", keyValuePairs);
        return keyValuePairsRepository.save(keyValuePairs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KeyValuePairs> findAll(Pageable pageable) {
        log.debug("Request to get all KeyValuePairs");
        return keyValuePairsRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<KeyValuePairs> findOne(Long id) {
        log.debug("Request to get KeyValuePairs : {}", id);
        return keyValuePairsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KeyValuePairs : {}", id);
        keyValuePairsRepository.deleteById(id);
    }
}
