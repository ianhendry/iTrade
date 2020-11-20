package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.TradeJournalPostService;
import com.gracefl.tradeplus.domain.TradeJournalPost;
import com.gracefl.tradeplus.repository.TradeJournalPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TradeJournalPost}.
 */
@Service
@Transactional
public class TradeJournalPostServiceImpl implements TradeJournalPostService {

    private final Logger log = LoggerFactory.getLogger(TradeJournalPostServiceImpl.class);

    private final TradeJournalPostRepository tradeJournalPostRepository;

    public TradeJournalPostServiceImpl(TradeJournalPostRepository tradeJournalPostRepository) {
        this.tradeJournalPostRepository = tradeJournalPostRepository;
    }

    @Override
    public TradeJournalPost save(TradeJournalPost tradeJournalPost) {
        log.debug("Request to save TradeJournalPost : {}", tradeJournalPost);
        return tradeJournalPostRepository.save(tradeJournalPost);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TradeJournalPost> findAll(Pageable pageable) {
        log.debug("Request to get all TradeJournalPosts");
        return tradeJournalPostRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TradeJournalPost> findOne(Long id) {
        log.debug("Request to get TradeJournalPost : {}", id);
        return tradeJournalPostRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TradeJournalPost : {}", id);
        tradeJournalPostRepository.deleteById(id);
    }
}
