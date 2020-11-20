package com.gracefl.tradeplus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gracefl.tradeplus.web.rest.TestUtil;

public class SignalSequencesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SignalSequences.class);
        SignalSequences signalSequences1 = new SignalSequences();
        signalSequences1.setId(1L);
        SignalSequences signalSequences2 = new SignalSequences();
        signalSequences2.setId(signalSequences1.getId());
        assertThat(signalSequences1).isEqualTo(signalSequences2);
        signalSequences2.setId(2L);
        assertThat(signalSequences1).isNotEqualTo(signalSequences2);
        signalSequences1.setId(null);
        assertThat(signalSequences1).isNotEqualTo(signalSequences2);
    }
}
