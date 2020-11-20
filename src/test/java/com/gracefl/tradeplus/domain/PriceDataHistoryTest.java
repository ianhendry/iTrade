package com.gracefl.tradeplus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gracefl.tradeplus.web.rest.TestUtil;

public class PriceDataHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceDataHistory.class);
        PriceDataHistory priceDataHistory1 = new PriceDataHistory();
        priceDataHistory1.setId(1L);
        PriceDataHistory priceDataHistory2 = new PriceDataHistory();
        priceDataHistory2.setId(priceDataHistory1.getId());
        assertThat(priceDataHistory1).isEqualTo(priceDataHistory2);
        priceDataHistory2.setId(2L);
        assertThat(priceDataHistory1).isNotEqualTo(priceDataHistory2);
        priceDataHistory1.setId(null);
        assertThat(priceDataHistory1).isNotEqualTo(priceDataHistory2);
    }
}
