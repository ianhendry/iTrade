package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.DailyAnalysisPostService;
import com.gracefl.tradeplus.domain.DailyAnalysisPost;
import com.gracefl.tradeplus.repository.DailyAnalysisPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DailyAnalysisPost}.
 */
@Service
@Transactional
public class DailyAnalysisPostServiceImpl implements DailyAnalysisPostService {

    private final Logger log = LoggerFactory.getLogger(DailyAnalysisPostServiceImpl.class);

    private final DailyAnalysisPostRepository dailyAnalysisPostRepository;

    public DailyAnalysisPostServiceImpl(DailyAnalysisPostRepository dailyAnalysisPostRepository) {
        this.dailyAnalysisPostRepository = dailyAnalysisPostRepository;
    }

    @Override
    public DailyAnalysisPost save(DailyAnalysisPost dailyAnalysisPost) {
        log.debug("Request to save DailyAnalysisPost : {}", dailyAnalysisPost);
        return dailyAnalysisPostRepository.save(dailyAnalysisPost);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DailyAnalysisPost> findAll(Pageable pageable) {
        log.debug("Request to get all DailyAnalysisPosts");
        return dailyAnalysisPostRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DailyAnalysisPost> findOne(Long id) {
        log.debug("Request to get DailyAnalysisPost : {}", id);
        return dailyAnalysisPostRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DailyAnalysisPost : {}", id);
        dailyAnalysisPostRepository.deleteById(id);
    }
}
