package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.InstrumentService;
import com.gracefl.tradeplus.domain.Instrument;
import com.gracefl.tradeplus.repository.InstrumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Instrument}.
 */
@Service
@Transactional
public class InstrumentServiceImpl implements InstrumentService {

    private final Logger log = LoggerFactory.getLogger(InstrumentServiceImpl.class);

    private final InstrumentRepository instrumentRepository;

    public InstrumentServiceImpl(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    @Override
    public Instrument save(Instrument instrument) {
        log.debug("Request to save Instrument : {}", instrument);
        return instrumentRepository.save(instrument);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Instrument> findAll(Pageable pageable) {
        log.debug("Request to get all Instruments");
        return instrumentRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Instrument> findOne(Long id) {
        log.debug("Request to get Instrument : {}", id);
        return instrumentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Instrument : {}", id);
        instrumentRepository.deleteById(id);
    }
}
