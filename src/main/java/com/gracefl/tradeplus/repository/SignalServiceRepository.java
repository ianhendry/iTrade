package com.gracefl.tradeplus.repository;

import com.gracefl.tradeplus.domain.SignalService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the SignalService entity.
 */
@Repository
public interface SignalServiceRepository extends JpaRepository<SignalService, Long> {

    @Query(value = "select distinct signalService from SignalService signalService left join fetch signalService.intruments",
        countQuery = "select count(distinct signalService) from SignalService signalService")
    Page<SignalService> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct signalService from SignalService signalService left join fetch signalService.intruments")
    List<SignalService> findAllWithEagerRelationships();

    @Query("select signalService from SignalService signalService left join fetch signalService.intruments where signalService.id =:id")
    Optional<SignalService> findOneWithEagerRelationships(@Param("id") Long id);
}
