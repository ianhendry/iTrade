package com.gracefl.tradeplus.repository;

import com.gracefl.tradeplus.domain.ShippingDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ShippingDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShippingDetailsRepository extends JpaRepository<ShippingDetails, Long> {

    @Query("select shippingDetails from ShippingDetails shippingDetails where shippingDetails.user.login = ?#{principal.username}")
    List<ShippingDetails> findByUserIsCurrentUser();
}
