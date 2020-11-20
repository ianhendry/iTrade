package com.gracefl.tradeplus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gracefl.tradeplus.web.rest.TestUtil;

public class SignalServiceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SignalService.class);
        SignalService signalService1 = new SignalService();
        signalService1.setId(1L);
        SignalService signalService2 = new SignalService();
        signalService2.setId(signalService1.getId());
        assertThat(signalService1).isEqualTo(signalService2);
        signalService2.setId(2L);
        assertThat(signalService1).isNotEqualTo(signalService2);
        signalService1.setId(null);
        assertThat(signalService1).isNotEqualTo(signalService2);
    }
}
