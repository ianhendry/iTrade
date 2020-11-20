package com.gracefl.tradeplus.repository;

import com.gracefl.tradeplus.domain.Mt4Account;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Mt4Account entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Mt4AccountRepository extends JpaRepository<Mt4Account, Long> {
}
