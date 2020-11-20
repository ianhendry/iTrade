package com.gracefl.tradeplus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gracefl.tradeplus.web.rest.TestUtil;

public class VideoPostTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoPost.class);
        VideoPost videoPost1 = new VideoPost();
        videoPost1.setId(1L);
        VideoPost videoPost2 = new VideoPost();
        videoPost2.setId(videoPost1.getId());
        assertThat(videoPost1).isEqualTo(videoPost2);
        videoPost2.setId(2L);
        assertThat(videoPost1).isNotEqualTo(videoPost2);
        videoPost1.setId(null);
        assertThat(videoPost1).isNotEqualTo(videoPost2);
    }
}
