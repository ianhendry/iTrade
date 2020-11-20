package com.gracefl.tradeplus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gracefl.tradeplus.web.rest.TestUtil;

public class Mt4TradeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mt4Trade.class);
        Mt4Trade mt4Trade1 = new Mt4Trade();
        mt4Trade1.setId(1L);
        Mt4Trade mt4Trade2 = new Mt4Trade();
        mt4Trade2.setId(mt4Trade1.getId());
        assertThat(mt4Trade1).isEqualTo(mt4Trade2);
        mt4Trade2.setId(2L);
        assertThat(mt4Trade1).isNotEqualTo(mt4Trade2);
        mt4Trade1.setId(null);
        assertThat(mt4Trade1).isNotEqualTo(mt4Trade2);
    }
}
