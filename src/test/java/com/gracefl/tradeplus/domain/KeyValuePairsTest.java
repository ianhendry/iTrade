package com.gracefl.tradeplus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gracefl.tradeplus.web.rest.TestUtil;

public class KeyValuePairsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyValuePairs.class);
        KeyValuePairs keyValuePairs1 = new KeyValuePairs();
        keyValuePairs1.setId(1L);
        KeyValuePairs keyValuePairs2 = new KeyValuePairs();
        keyValuePairs2.setId(keyValuePairs1.getId());
        assertThat(keyValuePairs1).isEqualTo(keyValuePairs2);
        keyValuePairs2.setId(2L);
        assertThat(keyValuePairs1).isNotEqualTo(keyValuePairs2);
        keyValuePairs1.setId(null);
        assertThat(keyValuePairs1).isNotEqualTo(keyValuePairs2);
    }
}
