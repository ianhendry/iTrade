package com.gracefl.tradeplus.repository;

import com.gracefl.tradeplus.domain.TradeJournalPost;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TradeJournalPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TradeJournalPostRepository extends JpaRepository<TradeJournalPost, Long> {
}
