package com.gracefl.tradeplus.repository;

import com.gracefl.tradeplus.domain.VideoPost;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VideoPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoPostRepository extends JpaRepository<VideoPost, Long> {
}
