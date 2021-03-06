package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.SiteAccountService;
import com.gracefl.tradeplus.domain.SiteAccount;
import com.gracefl.tradeplus.repository.SiteAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SiteAccount}.
 */
@Service
@Transactional
public class SiteAccountServiceImpl implements SiteAccountService {

    private final Logger log = LoggerFactory.getLogger(SiteAccountServiceImpl.class);

    private final SiteAccountRepository siteAccountRepository;

    public SiteAccountServiceImpl(SiteAccountRepository siteAccountRepository) {
        this.siteAccountRepository = siteAccountRepository;
    }

    @Override
    public SiteAccount save(SiteAccount siteAccount) {
        log.debug("Request to save SiteAccount : {}", siteAccount);
        return siteAccountRepository.save(siteAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SiteAccount> findAll(Pageable pageable) {
        log.debug("Request to get all SiteAccounts");
        return siteAccountRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SiteAccount> findOne(Long id) {
        log.debug("Request to get SiteAccount : {}", id);
        return siteAccountRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SiteAccount : {}", id);
        siteAccountRepository.deleteById(id);
    }
}
