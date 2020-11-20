package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.DailyAnalysisPost;
import com.gracefl.tradeplus.repository.DailyAnalysisPostRepository;
import com.gracefl.tradeplus.service.DailyAnalysisPostService;

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

import com.gracefl.tradeplus.domain.enumeration.DAYOFWEEK;
import com.gracefl.tradeplus.domain.enumeration.HIGHVOLBARLOCATION;
/**
 * Integration tests for the {@link DailyAnalysisPostResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DailyAnalysisPostResourceIT {

    private static final String DEFAULT_POST_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_POST_TITLE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_ADDED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ADDED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final DAYOFWEEK DEFAULT_DAY_OF_WEEK = DAYOFWEEK.MONDAY;
    private static final DAYOFWEEK UPDATED_DAY_OF_WEEK = DAYOFWEEK.TUESDAY;

    private static final String DEFAULT_BACKGROUND_VOLUME = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_VOLUME = "BBBBBBBBBB";

    private static final String DEFAULT_PRICE_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_PRICE_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_REASONS_TO_ENTER = "AAAAAAAAAA";
    private static final String UPDATED_REASONS_TO_ENTER = "BBBBBBBBBB";

    private static final String DEFAULT_WARNING_SIGNS = "AAAAAAAAAA";
    private static final String UPDATED_WARNING_SIGNS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DAILY_CHART_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DAILY_CHART_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DAILY_CHART_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DAILY_CHART_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ONE_HR_CHART_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ONE_HR_CHART_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ONE_HR_CHART_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ONE_HR_CHART_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FIVE_MIN_CHART_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FIVE_MIN_CHART_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FIVE_MIN_CHART_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FIVE_MIN_CHART_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PLAN_FOR_TODAY = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_FOR_TODAY = "BBBBBBBBBB";

    private static final String DEFAULT_HIGH_VOL_BAR = "AAAAAAAAAA";
    private static final String UPDATED_HIGH_VOL_BAR = "BBBBBBBBBB";

    private static final HIGHVOLBARLOCATION DEFAULT_HIGH_VOL_BAR_LOCATION = HIGHVOLBARLOCATION.TOUCHING;
    private static final HIGHVOLBARLOCATION UPDATED_HIGH_VOL_BAR_LOCATION = HIGHVOLBARLOCATION.CROSSING;

    @Autowired
    private DailyAnalysisPostRepository dailyAnalysisPostRepository;

    @Autowired
    private DailyAnalysisPostService dailyAnalysisPostService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDailyAnalysisPostMockMvc;

    private DailyAnalysisPost dailyAnalysisPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyAnalysisPost createEntity(EntityManager em) {
        DailyAnalysisPost dailyAnalysisPost = new DailyAnalysisPost()
            .postTitle(DEFAULT_POST_TITLE)
            .dateAdded(DEFAULT_DATE_ADDED)
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .backgroundVolume(DEFAULT_BACKGROUND_VOLUME)
            .priceAction(DEFAULT_PRICE_ACTION)
            .reasonsToEnter(DEFAULT_REASONS_TO_ENTER)
            .warningSigns(DEFAULT_WARNING_SIGNS)
            .dailyChartImage(DEFAULT_DAILY_CHART_IMAGE)
            .dailyChartImageContentType(DEFAULT_DAILY_CHART_IMAGE_CONTENT_TYPE)
            .oneHrChartImage(DEFAULT_ONE_HR_CHART_IMAGE)
            .oneHrChartImageContentType(DEFAULT_ONE_HR_CHART_IMAGE_CONTENT_TYPE)
            .fiveMinChartImage(DEFAULT_FIVE_MIN_CHART_IMAGE)
            .fiveMinChartImageContentType(DEFAULT_FIVE_MIN_CHART_IMAGE_CONTENT_TYPE)
            .planForToday(DEFAULT_PLAN_FOR_TODAY)
            .highVolBar(DEFAULT_HIGH_VOL_BAR)
            .highVolBarLocation(DEFAULT_HIGH_VOL_BAR_LOCATION);
        return dailyAnalysisPost;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyAnalysisPost createUpdatedEntity(EntityManager em) {
        DailyAnalysisPost dailyAnalysisPost = new DailyAnalysisPost()
            .postTitle(UPDATED_POST_TITLE)
            .dateAdded(UPDATED_DATE_ADDED)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .backgroundVolume(UPDATED_BACKGROUND_VOLUME)
            .priceAction(UPDATED_PRICE_ACTION)
            .reasonsToEnter(UPDATED_REASONS_TO_ENTER)
            .warningSigns(UPDATED_WARNING_SIGNS)
            .dailyChartImage(UPDATED_DAILY_CHART_IMAGE)
            .dailyChartImageContentType(UPDATED_DAILY_CHART_IMAGE_CONTENT_TYPE)
            .oneHrChartImage(UPDATED_ONE_HR_CHART_IMAGE)
            .oneHrChartImageContentType(UPDATED_ONE_HR_CHART_IMAGE_CONTENT_TYPE)
            .fiveMinChartImage(UPDATED_FIVE_MIN_CHART_IMAGE)
            .fiveMinChartImageContentType(UPDATED_FIVE_MIN_CHART_IMAGE_CONTENT_TYPE)
            .planForToday(UPDATED_PLAN_FOR_TODAY)
            .highVolBar(UPDATED_HIGH_VOL_BAR)
            .highVolBarLocation(UPDATED_HIGH_VOL_BAR_LOCATION);
        return dailyAnalysisPost;
    }

    @BeforeEach
    public void initTest() {
        dailyAnalysisPost = createEntity(em);
    }

    @Test
    @Transactional
    public void createDailyAnalysisPost() throws Exception {
        int databaseSizeBeforeCreate = dailyAnalysisPostRepository.findAll().size();
        // Create the DailyAnalysisPost
        restDailyAnalysisPostMockMvc.perform(post("/api/daily-analysis-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyAnalysisPost)))
            .andExpect(status().isCreated());

        // Validate the DailyAnalysisPost in the database
        List<DailyAnalysisPost> dailyAnalysisPostList = dailyAnalysisPostRepository.findAll();
        assertThat(dailyAnalysisPostList).hasSize(databaseSizeBeforeCreate + 1);
        DailyAnalysisPost testDailyAnalysisPost = dailyAnalysisPostList.get(dailyAnalysisPostList.size() - 1);
        assertThat(testDailyAnalysisPost.getPostTitle()).isEqualTo(DEFAULT_POST_TITLE);
        assertThat(testDailyAnalysisPost.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testDailyAnalysisPost.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testDailyAnalysisPost.getBackgroundVolume()).isEqualTo(DEFAULT_BACKGROUND_VOLUME);
        assertThat(testDailyAnalysisPost.getPriceAction()).isEqualTo(DEFAULT_PRICE_ACTION);
        assertThat(testDailyAnalysisPost.getReasonsToEnter()).isEqualTo(DEFAULT_REASONS_TO_ENTER);
        assertThat(testDailyAnalysisPost.getWarningSigns()).isEqualTo(DEFAULT_WARNING_SIGNS);
        assertThat(testDailyAnalysisPost.getDailyChartImage()).isEqualTo(DEFAULT_DAILY_CHART_IMAGE);
        assertThat(testDailyAnalysisPost.getDailyChartImageContentType()).isEqualTo(DEFAULT_DAILY_CHART_IMAGE_CONTENT_TYPE);
        assertThat(testDailyAnalysisPost.getOneHrChartImage()).isEqualTo(DEFAULT_ONE_HR_CHART_IMAGE);
        assertThat(testDailyAnalysisPost.getOneHrChartImageContentType()).isEqualTo(DEFAULT_ONE_HR_CHART_IMAGE_CONTENT_TYPE);
        assertThat(testDailyAnalysisPost.getFiveMinChartImage()).isEqualTo(DEFAULT_FIVE_MIN_CHART_IMAGE);
        assertThat(testDailyAnalysisPost.getFiveMinChartImageContentType()).isEqualTo(DEFAULT_FIVE_MIN_CHART_IMAGE_CONTENT_TYPE);
        assertThat(testDailyAnalysisPost.getPlanForToday()).isEqualTo(DEFAULT_PLAN_FOR_TODAY);
        assertThat(testDailyAnalysisPost.getHighVolBar()).isEqualTo(DEFAULT_HIGH_VOL_BAR);
        assertThat(testDailyAnalysisPost.getHighVolBarLocation()).isEqualTo(DEFAULT_HIGH_VOL_BAR_LOCATION);
    }

    @Test
    @Transactional
    public void createDailyAnalysisPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dailyAnalysisPostRepository.findAll().size();

        // Create the DailyAnalysisPost with an existing ID
        dailyAnalysisPost.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyAnalysisPostMockMvc.perform(post("/api/daily-analysis-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyAnalysisPost)))
            .andExpect(status().isBadRequest());

        // Validate the DailyAnalysisPost in the database
        List<DailyAnalysisPost> dailyAnalysisPostList = dailyAnalysisPostRepository.findAll();
        assertThat(dailyAnalysisPostList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPostTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyAnalysisPostRepository.findAll().size();
        // set the field null
        dailyAnalysisPost.setPostTitle(null);

        // Create the DailyAnalysisPost, which fails.


        restDailyAnalysisPostMockMvc.perform(post("/api/daily-analysis-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyAnalysisPost)))
            .andExpect(status().isBadRequest());

        List<DailyAnalysisPost> dailyAnalysisPostList = dailyAnalysisPostRepository.findAll();
        assertThat(dailyAnalysisPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateAddedIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyAnalysisPostRepository.findAll().size();
        // set the field null
        dailyAnalysisPost.setDateAdded(null);

        // Create the DailyAnalysisPost, which fails.


        restDailyAnalysisPostMockMvc.perform(post("/api/daily-analysis-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyAnalysisPost)))
            .andExpect(status().isBadRequest());

        List<DailyAnalysisPost> dailyAnalysisPostList = dailyAnalysisPostRepository.findAll();
        assertThat(dailyAnalysisPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDayOfWeekIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyAnalysisPostRepository.findAll().size();
        // set the field null
        dailyAnalysisPost.setDayOfWeek(null);

        // Create the DailyAnalysisPost, which fails.


        restDailyAnalysisPostMockMvc.perform(post("/api/daily-analysis-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyAnalysisPost)))
            .andExpect(status().isBadRequest());

        List<DailyAnalysisPost> dailyAnalysisPostList = dailyAnalysisPostRepository.findAll();
        assertThat(dailyAnalysisPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBackgroundVolumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyAnalysisPostRepository.findAll().size();
        // set the field null
        dailyAnalysisPost.setBackgroundVolume(null);

        // Create the DailyAnalysisPost, which fails.


        restDailyAnalysisPostMockMvc.perform(post("/api/daily-analysis-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyAnalysisPost)))
            .andExpect(status().isBadRequest());

        List<DailyAnalysisPost> dailyAnalysisPostList = dailyAnalysisPostRepository.findAll();
        assertThat(dailyAnalysisPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyAnalysisPostRepository.findAll().size();
        // set the field null
        dailyAnalysisPost.setPriceAction(null);

        // Create the DailyAnalysisPost, which fails.


        restDailyAnalysisPostMockMvc.perform(post("/api/daily-analysis-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyAnalysisPost)))
            .andExpect(status().isBadRequest());

        List<DailyAnalysisPost> dailyAnalysisPostList = dailyAnalysisPostRepository.findAll();
        assertThat(dailyAnalysisPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDailyAnalysisPosts() throws Exception {
        // Initialize the database
        dailyAnalysisPostRepository.saveAndFlush(dailyAnalysisPost);

        // Get all the dailyAnalysisPostList
        restDailyAnalysisPostMockMvc.perform(get("/api/daily-analysis-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyAnalysisPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].postTitle").value(hasItem(DEFAULT_POST_TITLE)))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(sameInstant(DEFAULT_DATE_ADDED))))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK.toString())))
            .andExpect(jsonPath("$.[*].backgroundVolume").value(hasItem(DEFAULT_BACKGROUND_VOLUME)))
            .andExpect(jsonPath("$.[*].priceAction").value(hasItem(DEFAULT_PRICE_ACTION)))
            .andExpect(jsonPath("$.[*].reasonsToEnter").value(hasItem(DEFAULT_REASONS_TO_ENTER)))
            .andExpect(jsonPath("$.[*].warningSigns").value(hasItem(DEFAULT_WARNING_SIGNS)))
            .andExpect(jsonPath("$.[*].dailyChartImageContentType").value(hasItem(DEFAULT_DAILY_CHART_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dailyChartImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_DAILY_CHART_IMAGE))))
            .andExpect(jsonPath("$.[*].oneHrChartImageContentType").value(hasItem(DEFAULT_ONE_HR_CHART_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].oneHrChartImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ONE_HR_CHART_IMAGE))))
            .andExpect(jsonPath("$.[*].fiveMinChartImageContentType").value(hasItem(DEFAULT_FIVE_MIN_CHART_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fiveMinChartImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_FIVE_MIN_CHART_IMAGE))))
            .andExpect(jsonPath("$.[*].planForToday").value(hasItem(DEFAULT_PLAN_FOR_TODAY)))
            .andExpect(jsonPath("$.[*].highVolBar").value(hasItem(DEFAULT_HIGH_VOL_BAR)))
            .andExpect(jsonPath("$.[*].highVolBarLocation").value(hasItem(DEFAULT_HIGH_VOL_BAR_LOCATION.toString())));
    }
    
    @Test
    @Transactional
    public void getDailyAnalysisPost() throws Exception {
        // Initialize the database
        dailyAnalysisPostRepository.saveAndFlush(dailyAnalysisPost);

        // Get the dailyAnalysisPost
        restDailyAnalysisPostMockMvc.perform(get("/api/daily-analysis-posts/{id}", dailyAnalysisPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dailyAnalysisPost.getId().intValue()))
            .andExpect(jsonPath("$.postTitle").value(DEFAULT_POST_TITLE))
            .andExpect(jsonPath("$.dateAdded").value(sameInstant(DEFAULT_DATE_ADDED)))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK.toString()))
            .andExpect(jsonPath("$.backgroundVolume").value(DEFAULT_BACKGROUND_VOLUME))
            .andExpect(jsonPath("$.priceAction").value(DEFAULT_PRICE_ACTION))
            .andExpect(jsonPath("$.reasonsToEnter").value(DEFAULT_REASONS_TO_ENTER))
            .andExpect(jsonPath("$.warningSigns").value(DEFAULT_WARNING_SIGNS))
            .andExpect(jsonPath("$.dailyChartImageContentType").value(DEFAULT_DAILY_CHART_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.dailyChartImage").value(Base64Utils.encodeToString(DEFAULT_DAILY_CHART_IMAGE)))
            .andExpect(jsonPath("$.oneHrChartImageContentType").value(DEFAULT_ONE_HR_CHART_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.oneHrChartImage").value(Base64Utils.encodeToString(DEFAULT_ONE_HR_CHART_IMAGE)))
            .andExpect(jsonPath("$.fiveMinChartImageContentType").value(DEFAULT_FIVE_MIN_CHART_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.fiveMinChartImage").value(Base64Utils.encodeToString(DEFAULT_FIVE_MIN_CHART_IMAGE)))
            .andExpect(jsonPath("$.planForToday").value(DEFAULT_PLAN_FOR_TODAY))
            .andExpect(jsonPath("$.highVolBar").value(DEFAULT_HIGH_VOL_BAR))
            .andExpect(jsonPath("$.highVolBarLocation").value(DEFAULT_HIGH_VOL_BAR_LOCATION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDailyAnalysisPost() throws Exception {
        // Get the dailyAnalysisPost
        restDailyAnalysisPostMockMvc.perform(get("/api/daily-analysis-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDailyAnalysisPost() throws Exception {
        // Initialize the database
        dailyAnalysisPostService.save(dailyAnalysisPost);

        int databaseSizeBeforeUpdate = dailyAnalysisPostRepository.findAll().size();

        // Update the dailyAnalysisPost
        DailyAnalysisPost updatedDailyAnalysisPost = dailyAnalysisPostRepository.findById(dailyAnalysisPost.getId()).get();
        // Disconnect from session so that the updates on updatedDailyAnalysisPost are not directly saved in db
        em.detach(updatedDailyAnalysisPost);
        updatedDailyAnalysisPost
            .postTitle(UPDATED_POST_TITLE)
            .dateAdded(UPDATED_DATE_ADDED)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .backgroundVolume(UPDATED_BACKGROUND_VOLUME)
            .priceAction(UPDATED_PRICE_ACTION)
            .reasonsToEnter(UPDATED_REASONS_TO_ENTER)
            .warningSigns(UPDATED_WARNING_SIGNS)
            .dailyChartImage(UPDATED_DAILY_CHART_IMAGE)
            .dailyChartImageContentType(UPDATED_DAILY_CHART_IMAGE_CONTENT_TYPE)
            .oneHrChartImage(UPDATED_ONE_HR_CHART_IMAGE)
            .oneHrChartImageContentType(UPDATED_ONE_HR_CHART_IMAGE_CONTENT_TYPE)
            .fiveMinChartImage(UPDATED_FIVE_MIN_CHART_IMAGE)
            .fiveMinChartImageContentType(UPDATED_FIVE_MIN_CHART_IMAGE_CONTENT_TYPE)
            .planForToday(UPDATED_PLAN_FOR_TODAY)
            .highVolBar(UPDATED_HIGH_VOL_BAR)
            .highVolBarLocation(UPDATED_HIGH_VOL_BAR_LOCATION);

        restDailyAnalysisPostMockMvc.perform(put("/api/daily-analysis-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDailyAnalysisPost)))
            .andExpect(status().isOk());

        // Validate the DailyAnalysisPost in the database
        List<DailyAnalysisPost> dailyAnalysisPostList = dailyAnalysisPostRepository.findAll();
        assertThat(dailyAnalysisPostList).hasSize(databaseSizeBeforeUpdate);
        DailyAnalysisPost testDailyAnalysisPost = dailyAnalysisPostList.get(dailyAnalysisPostList.size() - 1);
        assertThat(testDailyAnalysisPost.getPostTitle()).isEqualTo(UPDATED_POST_TITLE);
        assertThat(testDailyAnalysisPost.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testDailyAnalysisPost.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testDailyAnalysisPost.getBackgroundVolume()).isEqualTo(UPDATED_BACKGROUND_VOLUME);
        assertThat(testDailyAnalysisPost.getPriceAction()).isEqualTo(UPDATED_PRICE_ACTION);
        assertThat(testDailyAnalysisPost.getReasonsToEnter()).isEqualTo(UPDATED_REASONS_TO_ENTER);
        assertThat(testDailyAnalysisPost.getWarningSigns()).isEqualTo(UPDATED_WARNING_SIGNS);
        assertThat(testDailyAnalysisPost.getDailyChartImage()).isEqualTo(UPDATED_DAILY_CHART_IMAGE);
        assertThat(testDailyAnalysisPost.getDailyChartImageContentType()).isEqualTo(UPDATED_DAILY_CHART_IMAGE_CONTENT_TYPE);
        assertThat(testDailyAnalysisPost.getOneHrChartImage()).isEqualTo(UPDATED_ONE_HR_CHART_IMAGE);
        assertThat(testDailyAnalysisPost.getOneHrChartImageContentType()).isEqualTo(UPDATED_ONE_HR_CHART_IMAGE_CONTENT_TYPE);
        assertThat(testDailyAnalysisPost.getFiveMinChartImage()).isEqualTo(UPDATED_FIVE_MIN_CHART_IMAGE);
        assertThat(testDailyAnalysisPost.getFiveMinChartImageContentType()).isEqualTo(UPDATED_FIVE_MIN_CHART_IMAGE_CONTENT_TYPE);
        assertThat(testDailyAnalysisPost.getPlanForToday()).isEqualTo(UPDATED_PLAN_FOR_TODAY);
        assertThat(testDailyAnalysisPost.getHighVolBar()).isEqualTo(UPDATED_HIGH_VOL_BAR);
        assertThat(testDailyAnalysisPost.getHighVolBarLocation()).isEqualTo(UPDATED_HIGH_VOL_BAR_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingDailyAnalysisPost() throws Exception {
        int databaseSizeBeforeUpdate = dailyAnalysisPostRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyAnalysisPostMockMvc.perform(put("/api/daily-analysis-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyAnalysisPost)))
            .andExpect(status().isBadRequest());

        // Validate the DailyAnalysisPost in the database
        List<DailyAnalysisPost> dailyAnalysisPostList = dailyAnalysisPostRepository.findAll();
        assertThat(dailyAnalysisPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDailyAnalysisPost() throws Exception {
        // Initialize the database
        dailyAnalysisPostService.save(dailyAnalysisPost);

        int databaseSizeBeforeDelete = dailyAnalysisPostRepository.findAll().size();

        // Delete the dailyAnalysisPost
        restDailyAnalysisPostMockMvc.perform(delete("/api/daily-analysis-posts/{id}", dailyAnalysisPost.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DailyAnalysisPost> dailyAnalysisPostList = dailyAnalysisPostRepository.findAll();
        assertThat(dailyAnalysisPostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
