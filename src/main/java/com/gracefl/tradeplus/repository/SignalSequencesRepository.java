package com.gracefl.tradeplus.repository;

import com.gracefl.tradeplus.domain.SignalSequences;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SignalSequences entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SignalSequencesRepository extends JpaRepository<SignalSequences, Long> {
}
