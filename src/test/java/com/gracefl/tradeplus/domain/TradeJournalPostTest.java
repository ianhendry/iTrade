package com.gracefl.tradeplus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gracefl.tradeplus.web.rest.TestUtil;

public class TradeJournalPostTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TradeJournalPost.class);
        TradeJournalPost tradeJournalPost1 = new TradeJournalPost();
        tradeJournalPost1.setId(1L);
        TradeJournalPost tradeJournalPost2 = new TradeJournalPost();
        tradeJournalPost2.setId(tradeJournalPost1.getId());
        assertThat(tradeJournalPost1).isEqualTo(tradeJournalPost2);
        tradeJournalPost2.setId(2L);
        assertThat(tradeJournalPost1).isNotEqualTo(tradeJournalPost2);
        tradeJournalPost1.setId(null);
        assertThat(tradeJournalPost1).isNotEqualTo(tradeJournalPost2);
    }
}
