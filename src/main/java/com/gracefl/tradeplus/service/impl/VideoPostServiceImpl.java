package com.gracefl.tradeplus.service.impl;

import com.gracefl.tradeplus.service.VideoPostService;
import com.gracefl.tradeplus.domain.VideoPost;
import com.gracefl.tradeplus.repository.VideoPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link VideoPost}.
 */
@Service
@Transactional
public class VideoPostServiceImpl implements VideoPostService {

    private final Logger log = LoggerFactory.getLogger(VideoPostServiceImpl.class);

    private final VideoPostRepository videoPostRepository;

    public VideoPostServiceImpl(VideoPostRepository videoPostRepository) {
        this.videoPostRepository = videoPostRepository;
    }

    @Override
    public VideoPost save(VideoPost videoPost) {
        log.debug("Request to save VideoPost : {}", videoPost);
        return videoPostRepository.save(videoPost);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VideoPost> findAll(Pageable pageable) {
        log.debug("Request to get all VideoPosts");
        return videoPostRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<VideoPost> findOne(Long id) {
        log.debug("Request to get VideoPost : {}", id);
        return videoPostRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VideoPost : {}", id);
        videoPostRepository.deleteById(id);
    }
}
