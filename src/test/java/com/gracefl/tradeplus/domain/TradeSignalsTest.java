package com.gracefl.tradeplus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gracefl.tradeplus.web.rest.TestUtil;

public class TradeSignalsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TradeSignals.class);
        TradeSignals tradeSignals1 = new TradeSignals();
        tradeSignals1.setId(1L);
        TradeSignals tradeSignals2 = new TradeSignals();
        tradeSignals2.setId(tradeSignals1.getId());
        assertThat(tradeSignals1).isEqualTo(tradeSignals2);
        tradeSignals2.setId(2L);
        assertThat(tradeSignals1).isNotEqualTo(tradeSignals2);
        tradeSignals1.setId(null);
        assertThat(tradeSignals1).isNotEqualTo(tradeSignals2);
    }
}
