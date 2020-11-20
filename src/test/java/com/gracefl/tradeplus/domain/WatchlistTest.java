package com.gracefl.tradeplus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gracefl.tradeplus.web.rest.TestUtil;

public class WatchlistTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Watchlist.class);
        Watchlist watchlist1 = new Watchlist();
        watchlist1.setId(1L);
        Watchlist watchlist2 = new Watchlist();
        watchlist2.setId(watchlist1.getId());
        assertThat(watchlist1).isEqualTo(watchlist2);
        watchlist2.setId(2L);
        assertThat(watchlist1).isNotEqualTo(watchlist2);
        watchlist1.setId(null);
        assertThat(watchlist1).isNotEqualTo(watchlist2);
    }
}
