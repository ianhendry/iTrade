package com.gracefl.tradeplus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gracefl.tradeplus.web.rest.TestUtil;

public class SiteAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteAccount.class);
        SiteAccount siteAccount1 = new SiteAccount();
        siteAccount1.setId(1L);
        SiteAccount siteAccount2 = new SiteAccount();
        siteAccount2.setId(siteAccount1.getId());
        assertThat(siteAccount1).isEqualTo(siteAccount2);
        siteAccount2.setId(2L);
        assertThat(siteAccount1).isNotEqualTo(siteAccount2);
        siteAccount1.setId(null);
        assertThat(siteAccount1).isNotEqualTo(siteAccount2);
    }
}
