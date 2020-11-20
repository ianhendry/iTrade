package com.gracefl.tradeplus.repository;

import com.gracefl.tradeplus.domain.KeyValuePairs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the KeyValuePairs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeyValuePairsRepository extends JpaRepository<KeyValuePairs, Long> {
}
