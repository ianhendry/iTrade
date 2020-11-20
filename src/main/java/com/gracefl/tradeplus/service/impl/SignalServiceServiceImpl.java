package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.SignalServiceService;
import com.gracefl.tradeplus.domain.SignalService;
import com.gracefl.tradeplus.repository.SignalServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SignalService}.
 */
@Service
@Transactional
public class SignalServiceServiceImpl implements SignalServiceService {

    private final Logger log = LoggerFactory.getLogger(SignalServiceServiceImpl.class);

    private final SignalServiceRepository signalServiceRepository;

    public SignalServiceServiceImpl(SignalServiceRepository signalServiceRepository) {
        this.signalServiceRepository = signalServiceRepository;
    }

    @Override
    public SignalService save(SignalService signalService) {
        log.debug("Request to save SignalService : {}", signalService);
        return signalServiceRepository.save(signalService);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SignalService> findAll(Pageable pageable) {
        log.debug("Request to get all SignalServices");
        return signalServiceRepository.findAll(pageable);
    }


    public Page<SignalService> findAllWithEagerRelationships(Pageable pageable) {
        return signalServiceRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SignalService> findOne(Long id) {
        log.debug("Request to get SignalService : {}", id);
        return signalServiceRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SignalService : {}", id);
        signalServiceRepository.deleteById(id);
    }
}
