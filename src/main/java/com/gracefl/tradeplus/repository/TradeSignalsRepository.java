package com.gracefl.tradeplus.repository;

import com.gracefl.tradeplus.domain.TradeSignals;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TradeSignals entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TradeSignalsRepository extends JpaRepository<TradeSignals, Long> {
}
