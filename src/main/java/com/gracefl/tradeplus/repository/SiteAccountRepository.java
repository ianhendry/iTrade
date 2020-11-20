package com.gracefl.tradeplus.repository;

import com.gracefl.tradeplus.domain.SiteAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the SiteAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteAccountRepository extends JpaRepository<SiteAccount, Long> {

    @Query("select siteAccount from SiteAccount siteAccount where siteAccount.user.login = ?#{principal.username}")
    List<SiteAccount> findByUserIsCurrentUser();
}
