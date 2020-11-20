package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.TradeJournalPost;
import com.gracefl.tradeplus.repository.TradeJournalPostRepository;
import com.gracefl.tradeplus.service.TradeJournalPostService;

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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TradeJournalPostResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TradeJournalPostResourceIT {

    private static final String DEFAULT_POST_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_POST_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_POST_BODY = "AAAAAAAAAA";
    private static final String UPDATED_POST_BODY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ADDED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ADDED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_APPROVED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_APPROVED = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_MEDIA_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIA_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIA_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIA_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_MEDIA_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIA_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIA_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIA_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_MEDIA_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIA_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIA_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIA_3_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_MEDIA_4 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIA_4 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIA_4_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIA_4_CONTENT_TYPE = "image/png";

    @Autowired
    private TradeJournalPostRepository tradeJournalPostRepository;

    @Autowired
    private TradeJournalPostService tradeJournalPostService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTradeJournalPostMockMvc;

    private TradeJournalPost tradeJournalPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TradeJournalPost createEntity(EntityManager em) {
        TradeJournalPost tradeJournalPost = new TradeJournalPost()
            .postTitle(DEFAULT_POST_TITLE)
            .postBody(DEFAULT_POST_BODY)
            .dateAdded(DEFAULT_DATE_ADDED)
            .dateApproved(DEFAULT_DATE_APPROVED)
            .media1(DEFAULT_MEDIA_1)
            .media1ContentType(DEFAULT_MEDIA_1_CONTENT_TYPE)
            .media2(DEFAULT_MEDIA_2)
            .media2ContentType(DEFAULT_MEDIA_2_CONTENT_TYPE)
            .media3(DEFAULT_MEDIA_3)
            .media3ContentType(DEFAULT_MEDIA_3_CONTENT_TYPE)
            .media4(DEFAULT_MEDIA_4)
            .media4ContentType(DEFAULT_MEDIA_4_CONTENT_TYPE);
        return tradeJournalPost;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TradeJournalPost createUpdatedEntity(EntityManager em) {
        TradeJournalPost tradeJournalPost = new TradeJournalPost()
            .postTitle(UPDATED_POST_TITLE)
            .postBody(UPDATED_POST_BODY)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateApproved(UPDATED_DATE_APPROVED)
            .media1(UPDATED_MEDIA_1)
            .media1ContentType(UPDATED_MEDIA_1_CONTENT_TYPE)
            .media2(UPDATED_MEDIA_2)
            .media2ContentType(UPDATED_MEDIA_2_CONTENT_TYPE)
            .media3(UPDATED_MEDIA_3)
            .media3ContentType(UPDATED_MEDIA_3_CONTENT_TYPE)
            .media4(UPDATED_MEDIA_4)
            .media4ContentType(UPDATED_MEDIA_4_CONTENT_TYPE);
        return tradeJournalPost;
    }

    @BeforeEach
    public void initTest() {
        tradeJournalPost = createEntity(em);
    }

    @Test
    @Transactional
    public void createTradeJournalPost() throws Exception {
        int databaseSizeBeforeCreate = tradeJournalPostRepository.findAll().size();
        // Create the TradeJournalPost
        restTradeJournalPostMockMvc.perform(post("/api/trade-journal-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradeJournalPost)))
            .andExpect(status().isCreated());

        // Validate the TradeJournalPost in the database
        List<TradeJournalPost> tradeJournalPostList = tradeJournalPostRepository.findAll();
        assertThat(tradeJournalPostList).hasSize(databaseSizeBeforeCreate + 1);
        TradeJournalPost testTradeJournalPost = tradeJournalPostList.get(tradeJournalPostList.size() - 1);
        assertThat(testTradeJournalPost.getPostTitle()).isEqualTo(DEFAULT_POST_TITLE);
        assertThat(testTradeJournalPost.getPostBody()).isEqualTo(DEFAULT_POST_BODY);
        assertThat(testTradeJournalPost.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testTradeJournalPost.getDateApproved()).isEqualTo(DEFAULT_DATE_APPROVED);
        assertThat(testTradeJournalPost.getMedia1()).isEqualTo(DEFAULT_MEDIA_1);
        assertThat(testTradeJournalPost.getMedia1ContentType()).isEqualTo(DEFAULT_MEDIA_1_CONTENT_TYPE);
        assertThat(testTradeJournalPost.getMedia2()).isEqualTo(DEFAULT_MEDIA_2);
        assertThat(testTradeJournalPost.getMedia2ContentType()).isEqualTo(DEFAULT_MEDIA_2_CONTENT_TYPE);
        assertThat(testTradeJournalPost.getMedia3()).isEqualTo(DEFAULT_MEDIA_3);
        assertThat(testTradeJournalPost.getMedia3ContentType()).isEqualTo(DEFAULT_MEDIA_3_CONTENT_TYPE);
        assertThat(testTradeJournalPost.getMedia4()).isEqualTo(DEFAULT_MEDIA_4);
        assertThat(testTradeJournalPost.getMedia4ContentType()).isEqualTo(DEFAULT_MEDIA_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createTradeJournalPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tradeJournalPostRepository.findAll().size();

        // Create the TradeJournalPost with an existing ID
        tradeJournalPost.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTradeJournalPostMockMvc.perform(post("/api/trade-journal-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradeJournalPost)))
            .andExpect(status().isBadRequest());

        // Validate the TradeJournalPost in the database
        List<TradeJournalPost> tradeJournalPostList = tradeJournalPostRepository.findAll();
        assertThat(tradeJournalPostList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPostTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradeJournalPostRepository.findAll().size();
        // set the field null
        tradeJournalPost.setPostTitle(null);

        // Create the TradeJournalPost, which fails.


        restTradeJournalPostMockMvc.perform(post("/api/trade-journal-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradeJournalPost)))
            .andExpect(status().isBadRequest());

        List<TradeJournalPost> tradeJournalPostList = tradeJournalPostRepository.findAll();
        assertThat(tradeJournalPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateAddedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradeJournalPostRepository.findAll().size();
        // set the field null
        tradeJournalPost.setDateAdded(null);

        // Create the TradeJournalPost, which fails.


        restTradeJournalPostMockMvc.perform(post("/api/trade-journal-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradeJournalPost)))
            .andExpect(status().isBadRequest());

        List<TradeJournalPost> tradeJournalPostList = tradeJournalPostRepository.findAll();
        assertThat(tradeJournalPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTradeJournalPosts() throws Exception {
        // Initialize the database
        tradeJournalPostRepository.saveAndFlush(tradeJournalPost);

        // Get all the tradeJournalPostList
        restTradeJournalPostMockMvc.perform(get("/api/trade-journal-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tradeJournalPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].postTitle").value(hasItem(DEFAULT_POST_TITLE)))
            .andExpect(jsonPath("$.[*].postBody").value(hasItem(DEFAULT_POST_BODY)))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].dateApproved").value(hasItem(DEFAULT_DATE_APPROVED.toString())))
            .andExpect(jsonPath("$.[*].media1ContentType").value(hasItem(DEFAULT_MEDIA_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].media1").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIA_1))))
            .andExpect(jsonPath("$.[*].media2ContentType").value(hasItem(DEFAULT_MEDIA_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].media2").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIA_2))))
            .andExpect(jsonPath("$.[*].media3ContentType").value(hasItem(DEFAULT_MEDIA_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].media3").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIA_3))))
            .andExpect(jsonPath("$.[*].media4ContentType").value(hasItem(DEFAULT_MEDIA_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].media4").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIA_4))));
    }
    
    @Test
    @Transactional
    public void getTradeJournalPost() throws Exception {
        // Initialize the database
        tradeJournalPostRepository.saveAndFlush(tradeJournalPost);

        // Get the tradeJournalPost
        restTradeJournalPostMockMvc.perform(get("/api/trade-journal-posts/{id}", tradeJournalPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tradeJournalPost.getId().intValue()))
            .andExpect(jsonPath("$.postTitle").value(DEFAULT_POST_TITLE))
            .andExpect(jsonPath("$.postBody").value(DEFAULT_POST_BODY))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED.toString()))
            .andExpect(jsonPath("$.dateApproved").value(DEFAULT_DATE_APPROVED.toString()))
            .andExpect(jsonPath("$.media1ContentType").value(DEFAULT_MEDIA_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.media1").value(Base64Utils.encodeToString(DEFAULT_MEDIA_1)))
            .andExpect(jsonPath("$.media2ContentType").value(DEFAULT_MEDIA_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.media2").value(Base64Utils.encodeToString(DEFAULT_MEDIA_2)))
            .andExpect(jsonPath("$.media3ContentType").value(DEFAULT_MEDIA_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.media3").value(Base64Utils.encodeToString(DEFAULT_MEDIA_3)))
            .andExpect(jsonPath("$.media4ContentType").value(DEFAULT_MEDIA_4_CONTENT_TYPE))
            .andExpect(jsonPath("$.media4").value(Base64Utils.encodeToString(DEFAULT_MEDIA_4)));
    }
    @Test
    @Transactional
    public void getNonExistingTradeJournalPost() throws Exception {
        // Get the tradeJournalPost
        restTradeJournalPostMockMvc.perform(get("/api/trade-journal-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTradeJournalPost() throws Exception {
        // Initialize the database
        tradeJournalPostService.save(tradeJournalPost);

        int databaseSizeBeforeUpdate = tradeJournalPostRepository.findAll().size();

        // Update the tradeJournalPost
        TradeJournalPost updatedTradeJournalPost = tradeJournalPostRepository.findById(tradeJournalPost.getId()).get();
        // Disconnect from session so that the updates on updatedTradeJournalPost are not directly saved in db
        em.detach(updatedTradeJournalPost);
        updatedTradeJournalPost
            .postTitle(UPDATED_POST_TITLE)
            .postBody(UPDATED_POST_BODY)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateApproved(UPDATED_DATE_APPROVED)
            .media1(UPDATED_MEDIA_1)
            .media1ContentType(UPDATED_MEDIA_1_CONTENT_TYPE)
            .media2(UPDATED_MEDIA_2)
            .media2ContentType(UPDATED_MEDIA_2_CONTENT_TYPE)
            .media3(UPDATED_MEDIA_3)
            .media3ContentType(UPDATED_MEDIA_3_CONTENT_TYPE)
            .media4(UPDATED_MEDIA_4)
            .media4ContentType(UPDATED_MEDIA_4_CONTENT_TYPE);

        restTradeJournalPostMockMvc.perform(put("/api/trade-journal-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTradeJournalPost)))
            .andExpect(status().isOk());

        // Validate the TradeJournalPost in the database
        List<TradeJournalPost> tradeJournalPostList = tradeJournalPostRepository.findAll();
        assertThat(tradeJournalPostList).hasSize(databaseSizeBeforeUpdate);
        TradeJournalPost testTradeJournalPost = tradeJournalPostList.get(tradeJournalPostList.size() - 1);
        assertThat(testTradeJournalPost.getPostTitle()).isEqualTo(UPDATED_POST_TITLE);
        assertThat(testTradeJournalPost.getPostBody()).isEqualTo(UPDATED_POST_BODY);
        assertThat(testTradeJournalPost.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testTradeJournalPost.getDateApproved()).isEqualTo(UPDATED_DATE_APPROVED);
        assertThat(testTradeJournalPost.getMedia1()).isEqualTo(UPDATED_MEDIA_1);
        assertThat(testTradeJournalPost.getMedia1ContentType()).isEqualTo(UPDATED_MEDIA_1_CONTENT_TYPE);
        assertThat(testTradeJournalPost.getMedia2()).isEqualTo(UPDATED_MEDIA_2);
        assertThat(testTradeJournalPost.getMedia2ContentType()).isEqualTo(UPDATED_MEDIA_2_CONTENT_TYPE);
        assertThat(testTradeJournalPost.getMedia3()).isEqualTo(UPDATED_MEDIA_3);
        assertThat(testTradeJournalPost.getMedia3ContentType()).isEqualTo(UPDATED_MEDIA_3_CONTENT_TYPE);
        assertThat(testTradeJournalPost.getMedia4()).isEqualTo(UPDATED_MEDIA_4);
        assertThat(testTradeJournalPost.getMedia4ContentType()).isEqualTo(UPDATED_MEDIA_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTradeJournalPost() throws Exception {
        int databaseSizeBeforeUpdate = tradeJournalPostRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTradeJournalPostMockMvc.perform(put("/api/trade-journal-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradeJournalPost)))
            .andExpect(status().isBadRequest());

        // Validate the TradeJournalPost in the database
        List<TradeJournalPost> tradeJournalPostList = tradeJournalPostRepository.findAll();
        assertThat(tradeJournalPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTradeJournalPost() throws Exception {
        // Initialize the database
        tradeJournalPostService.save(tradeJournalPost);

        int databaseSizeBeforeDelete = tradeJournalPostRepository.findAll().size();

        // Delete the tradeJournalPost
        restTradeJournalPostMockMvc.perform(delete("/api/trade-journal-posts/{id}", tradeJournalPost.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TradeJournalPost> tradeJournalPostList = tradeJournalPostRepository.findAll();
        assertThat(tradeJournalPostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
