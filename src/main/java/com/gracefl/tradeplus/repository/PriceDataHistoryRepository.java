package com.gracefl.tradeplus.repository;

import com.gracefl.tradeplus.domain.PriceDataHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PriceDataHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceDataHistoryRepository extends JpaRepository<PriceDataHistory, Long> {
}
