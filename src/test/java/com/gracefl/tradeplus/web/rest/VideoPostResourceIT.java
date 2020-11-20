package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.VideoPost;
import com.gracefl.tradeplus.repository.VideoPostRepository;
import com.gracefl.tradeplus.service.VideoPostService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.gracefl.tradeplus.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VideoPostResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VideoPostResourceIT {

    private static final String DEFAULT_POST_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_POST_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_POST_BODY = "AAAAAAAAAA";
    private static final String UPDATED_POST_BODY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_ADDED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ADDED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_DATE_APPROVED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_APPROVED = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_MEDIA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIA_CONTENT_TYPE = "image/png";

    @Autowired
    private VideoPostRepository videoPostRepository;

    @Autowired
    private VideoPostService videoPostService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVideoPostMockMvc;

    private VideoPost videoPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VideoPost createEntity(EntityManager em) {
        VideoPost videoPost = new VideoPost()
            .postTitle(DEFAULT_POST_TITLE)
            .postBody(DEFAULT_POST_BODY)
            .dateAdded(DEFAULT_DATE_ADDED)
            .dateApproved(DEFAULT_DATE_APPROVED)
            .media(DEFAULT_MEDIA)
            .mediaContentType(DEFAULT_MEDIA_CONTENT_TYPE);
        return videoPost;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VideoPost createUpdatedEntity(EntityManager em) {
        VideoPost videoPost = new VideoPost()
            .postTitle(UPDATED_POST_TITLE)
            .postBody(UPDATED_POST_BODY)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateApproved(UPDATED_DATE_APPROVED)
            .media(UPDATED_MEDIA)
            .mediaContentType(UPDATED_MEDIA_CONTENT_TYPE);
        return videoPost;
    }

    @BeforeEach
    public void initTest() {
        videoPost = createEntity(em);
    }

    @Test
    @Transactional
    public void createVideoPost() throws Exception {
        int databaseSizeBeforeCreate = videoPostRepository.findAll().size();
        // Create the VideoPost
        restVideoPostMockMvc.perform(post("/api/video-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(videoPost)))
            .andExpect(status().isCreated());

        // Validate the VideoPost in the database
        List<VideoPost> videoPostList = videoPostRepository.findAll();
        assertThat(videoPostList).hasSize(databaseSizeBeforeCreate + 1);
        VideoPost testVideoPost = videoPostList.get(videoPostList.size() - 1);
        assertThat(testVideoPost.getPostTitle()).isEqualTo(DEFAULT_POST_TITLE);
        assertThat(testVideoPost.getPostBody()).isEqualTo(DEFAULT_POST_BODY);
        assertThat(testVideoPost.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testVideoPost.getDateApproved()).isEqualTo(DEFAULT_DATE_APPROVED);
        assertThat(testVideoPost.getMedia()).isEqualTo(DEFAULT_MEDIA);
        assertThat(testVideoPost.getMediaContentType()).isEqualTo(DEFAULT_MEDIA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createVideoPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = videoPostRepository.findAll().size();

        // Create the VideoPost with an existing ID
        videoPost.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoPostMockMvc.perform(post("/api/video-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(videoPost)))
            .andExpect(status().isBadRequest());

        // Validate the VideoPost in the database
        List<VideoPost> videoPostList = videoPostRepository.findAll();
        assertThat(videoPostList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPostTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoPostRepository.findAll().size();
        // set the field null
        videoPost.setPostTitle(null);

        // Create the VideoPost, which fails.


        restVideoPostMockMvc.perform(post("/api/video-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(videoPost)))
            .andExpect(status().isBadRequest());

        List<VideoPost> videoPostList = videoPostRepository.findAll();
        assertThat(videoPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateAddedIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoPostRepository.findAll().size();
        // set the field null
        videoPost.setDateAdded(null);

        // Create the VideoPost, which fails.


        restVideoPostMockMvc.perform(post("/api/video-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(videoPost)))
            .andExpect(status().isBadRequest());

        List<VideoPost> videoPostList = videoPostRepository.findAll();
        assertThat(videoPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVideoPosts() throws Exception {
        // Initialize the database
        videoPostRepository.saveAndFlush(videoPost);

        // Get all the videoPostList
        restVideoPostMockMvc.perform(get("/api/video-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videoPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].postTitle").value(hasItem(DEFAULT_POST_TITLE)))
            .andExpect(jsonPath("$.[*].postBody").value(hasItem(DEFAULT_POST_BODY)))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(sameInstant(DEFAULT_DATE_ADDED))))
            .andExpect(jsonPath("$.[*].dateApproved").value(hasItem(DEFAULT_DATE_APPROVED.toString())))
            .andExpect(jsonPath("$.[*].mediaContentType").value(hasItem(DEFAULT_MEDIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].media").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIA))));
    }
    
    @Test
    @Transactional
    public void getVideoPost() throws Exception {
        // Initialize the database
        videoPostRepository.saveAndFlush(videoPost);

        // Get the videoPost
        restVideoPostMockMvc.perform(get("/api/video-posts/{id}", videoPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(videoPost.getId().intValue()))
            .andExpect(jsonPath("$.postTitle").value(DEFAULT_POST_TITLE))
            .andExpect(jsonPath("$.postBody").value(DEFAULT_POST_BODY))
            .andExpect(jsonPath("$.dateAdded").value(sameInstant(DEFAULT_DATE_ADDED)))
            .andExpect(jsonPath("$.dateApproved").value(DEFAULT_DATE_APPROVED.toString()))
            .andExpect(jsonPath("$.mediaContentType").value(DEFAULT_MEDIA_CONTENT_TYPE))
            .andExpect(jsonPath("$.media").value(Base64Utils.encodeToString(DEFAULT_MEDIA)));
    }
    @Test
    @Transactional
    public void getNonExistingVideoPost() throws Exception {
        // Get the videoPost
        restVideoPostMockMvc.perform(get("/api/video-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVideoPost() throws Exception {
        // Initialize the database
        videoPostService.save(videoPost);

        int databaseSizeBeforeUpdate = videoPostRepository.findAll().size();

        // Update the videoPost
        VideoPost updatedVideoPost = videoPostRepository.findById(videoPost.getId()).get();
        // Disconnect from session so that the updates on updatedVideoPost are not directly saved in db
        em.detach(updatedVideoPost);
        updatedVideoPost
            .postTitle(UPDATED_POST_TITLE)
            .postBody(UPDATED_POST_BODY)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateApproved(UPDATED_DATE_APPROVED)
            .media(UPDATED_MEDIA)
            .mediaContentType(UPDATED_MEDIA_CONTENT_TYPE);

        restVideoPostMockMvc.perform(put("/api/video-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVideoPost)))
            .andExpect(status().isOk());

        // Validate the VideoPost in the database
        List<VideoPost> videoPostList = videoPostRepository.findAll();
        assertThat(videoPostList).hasSize(databaseSizeBeforeUpdate);
        VideoPost testVideoPost = videoPostList.get(videoPostList.size() - 1);
        assertThat(testVideoPost.getPostTitle()).isEqualTo(UPDATED_POST_TITLE);
        assertThat(testVideoPost.getPostBody()).isEqualTo(UPDATED_POST_BODY);
        assertThat(testVideoPost.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testVideoPost.getDateApproved()).isEqualTo(UPDATED_DATE_APPROVED);
        assertThat(testVideoPost.getMedia()).isEqualTo(UPDATED_MEDIA);
        assertThat(testVideoPost.getMediaContentType()).isEqualTo(UPDATED_MEDIA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingVideoPost() throws Exception {
        int databaseSizeBeforeUpdate = videoPostRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoPostMockMvc.perform(put("/api/video-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(videoPost)))
            .andExpect(status().isBadRequest());

        // Validate the VideoPost in the database
        List<VideoPost> videoPostList = videoPostRepository.findAll();
        assertThat(videoPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVideoPost() throws Exception {
        // Initialize the database
        videoPostService.save(videoPost);

        int databaseSizeBeforeDelete = videoPostRepository.findAll().size();

        // Delete the videoPost
        restVideoPostMockMvc.perform(delete("/api/video-posts/{id}", videoPost.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VideoPost> videoPostList = videoPostRepository.findAll();
        assertThat(videoPostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
