package com.gracefl.tradeplus.repository;

import com.gracefl.tradeplus.domain.DailyAnalysisPost;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DailyAnalysisPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyAnalysisPostRepository extends JpaRepository<DailyAnalysisPost, Long> {
}
