package com.gracefl.tradeplus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gracefl.tradeplus.web.rest.TestUtil;

public class Mt4AccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mt4Account.class);
        Mt4Account mt4Account1 = new Mt4Account();
        mt4Account1.setId(1L);
        Mt4Account mt4Account2 = new Mt4Account();
        mt4Account2.setId(mt4Account1.getId());
        assertThat(mt4Account1).isEqualTo(mt4Account2);
        mt4Account2.setId(2L);
        assertThat(mt4Account1).isNotEqualTo(mt4Account2);
        mt4Account1.setId(null);
        assertThat(mt4Account1).isNotEqualTo(mt4Account2);
    }
}
