package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.SignalSequencesService;
import com.gracefl.tradeplus.domain.SignalSequences;
import com.gracefl.tradeplus.repository.SignalSequencesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SignalSequences}.
 */
@Service
@Transactional
public class SignalSequencesServiceImpl implements SignalSequencesService {

    private final Logger log = LoggerFactory.getLogger(SignalSequencesServiceImpl.class);

    private final SignalSequencesRepository signalSequencesRepository;

    public SignalSequencesServiceImpl(SignalSequencesRepository signalSequencesRepository) {
        this.signalSequencesRepository = signalSequencesRepository;
    }

    @Override
    public SignalSequences save(SignalSequences signalSequences) {
        log.debug("Request to save SignalSequences : {}", signalSequences);
        return signalSequencesRepository.save(signalSequences);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SignalSequences> findAll(Pageable pageable) {
        log.debug("Request to get all SignalSequences");
        return signalSequencesRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SignalSequences> findOne(Long id) {
        log.debug("Request to get SignalSequences : {}", id);
        return signalSequencesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SignalSequences : {}", id);
        signalSequencesRepository.deleteById(id);
    }
}
