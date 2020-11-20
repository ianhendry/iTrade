package com.gracefl.tradeplus.repository;

import com.gracefl.tradeplus.domain.Mt4Trade;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Mt4Trade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Mt4TradeRepository extends JpaRepository<Mt4Trade, Long> {
}
