package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.PriceDataHistory;
import com.gracefl.tradeplus.repository.PriceDataHistoryRepository;
import com.gracefl.tradeplus.service.PriceDataHistoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
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

import com.gracefl.tradeplus.domain.enumeration.TIMEFRAME;
/**
 * Integration tests for the {@link PriceDataHistoryResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PriceDataHistoryResourceIT {

    private static final TIMEFRAME DEFAULT_PRICE_TIMEFRAME = TIMEFRAME.M1;
    private static final TIMEFRAME UPDATED_PRICE_TIMEFRAME = TIMEFRAME.M5;

    private static final LocalDate DEFAULT_PRICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_PRICE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PRICE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_PRICE_OPEN = 1D;
    private static final Double UPDATED_PRICE_OPEN = 2D;

    private static final Double DEFAULT_PRICE_HIGH = 1D;
    private static final Double UPDATED_PRICE_HIGH = 2D;

    private static final Double DEFAULT_PRICE_LOW = 1D;
    private static final Double UPDATED_PRICE_LOW = 2D;

    private static final Double DEFAULT_PRICE_CLOSE = 1D;
    private static final Double UPDATED_PRICE_CLOSE = 2D;

    private static final Double DEFAULT_PRICE_VOLUME = 1D;
    private static final Double UPDATED_PRICE_VOLUME = 2D;

    @Autowired
    private PriceDataHistoryRepository priceDataHistoryRepository;

    @Autowired
    private PriceDataHistoryService priceDataHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPriceDataHistoryMockMvc;

    private PriceDataHistory priceDataHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceDataHistory createEntity(EntityManager em) {
        PriceDataHistory priceDataHistory = new PriceDataHistory()
            .priceTimeframe(DEFAULT_PRICE_TIMEFRAME)
            .priceDate(DEFAULT_PRICE_DATE)
            .priceTime(DEFAULT_PRICE_TIME)
            .priceOpen(DEFAULT_PRICE_OPEN)
            .priceHigh(DEFAULT_PRICE_HIGH)
            .priceLow(DEFAULT_PRICE_LOW)
            .priceClose(DEFAULT_PRICE_CLOSE)
            .priceVolume(DEFAULT_PRICE_VOLUME);
        return priceDataHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceDataHistory createUpdatedEntity(EntityManager em) {
        PriceDataHistory priceDataHistory = new PriceDataHistory()
            .priceTimeframe(UPDATED_PRICE_TIMEFRAME)
            .priceDate(UPDATED_PRICE_DATE)
            .priceTime(UPDATED_PRICE_TIME)
            .priceOpen(UPDATED_PRICE_OPEN)
            .priceHigh(UPDATED_PRICE_HIGH)
            .priceLow(UPDATED_PRICE_LOW)
            .priceClose(UPDATED_PRICE_CLOSE)
            .priceVolume(UPDATED_PRICE_VOLUME);
        return priceDataHistory;
    }

    @BeforeEach
    public void initTest() {
        priceDataHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriceDataHistory() throws Exception {
        int databaseSizeBeforeCreate = priceDataHistoryRepository.findAll().size();
        // Create the PriceDataHistory
        restPriceDataHistoryMockMvc.perform(post("/api/price-data-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(priceDataHistory)))
            .andExpect(status().isCreated());

        // Validate the PriceDataHistory in the database
        List<PriceDataHistory> priceDataHistoryList = priceDataHistoryRepository.findAll();
        assertThat(priceDataHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        PriceDataHistory testPriceDataHistory = priceDataHistoryList.get(priceDataHistoryList.size() - 1);
        assertThat(testPriceDataHistory.getPriceTimeframe()).isEqualTo(DEFAULT_PRICE_TIMEFRAME);
        assertThat(testPriceDataHistory.getPriceDate()).isEqualTo(DEFAULT_PRICE_DATE);
        assertThat(testPriceDataHistory.getPriceTime()).isEqualTo(DEFAULT_PRICE_TIME);
        assertThat(testPriceDataHistory.getPriceOpen()).isEqualTo(DEFAULT_PRICE_OPEN);
        assertThat(testPriceDataHistory.getPriceHigh()).isEqualTo(DEFAULT_PRICE_HIGH);
        assertThat(testPriceDataHistory.getPriceLow()).isEqualTo(DEFAULT_PRICE_LOW);
        assertThat(testPriceDataHistory.getPriceClose()).isEqualTo(DEFAULT_PRICE_CLOSE);
        assertThat(testPriceDataHistory.getPriceVolume()).isEqualTo(DEFAULT_PRICE_VOLUME);
    }

    @Test
    @Transactional
    public void createPriceDataHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceDataHistoryRepository.findAll().size();

        // Create the PriceDataHistory with an existing ID
        priceDataHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceDataHistoryMockMvc.perform(post("/api/price-data-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(priceDataHistory)))
            .andExpect(status().isBadRequest());

        // Validate the PriceDataHistory in the database
        List<PriceDataHistory> priceDataHistoryList = priceDataHistoryRepository.findAll();
        assertThat(priceDataHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPriceTimeframeIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceDataHistoryRepository.findAll().size();
        // set the field null
        priceDataHistory.setPriceTimeframe(null);

        // Create the PriceDataHistory, which fails.


        restPriceDataHistoryMockMvc.perform(post("/api/price-data-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(priceDataHistory)))
            .andExpect(status().isBadRequest());

        List<PriceDataHistory> priceDataHistoryList = priceDataHistoryRepository.findAll();
        assertThat(priceDataHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriceDataHistories() throws Exception {
        // Initialize the database
        priceDataHistoryRepository.saveAndFlush(priceDataHistory);

        // Get all the priceDataHistoryList
        restPriceDataHistoryMockMvc.perform(get("/api/price-data-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceDataHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].priceTimeframe").value(hasItem(DEFAULT_PRICE_TIMEFRAME.toString())))
            .andExpect(jsonPath("$.[*].priceDate").value(hasItem(DEFAULT_PRICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].priceTime").value(hasItem(sameInstant(DEFAULT_PRICE_TIME))))
            .andExpect(jsonPath("$.[*].priceOpen").value(hasItem(DEFAULT_PRICE_OPEN.doubleValue())))
            .andExpect(jsonPath("$.[*].priceHigh").value(hasItem(DEFAULT_PRICE_HIGH.doubleValue())))
            .andExpect(jsonPath("$.[*].priceLow").value(hasItem(DEFAULT_PRICE_LOW.doubleValue())))
            .andExpect(jsonPath("$.[*].priceClose").value(hasItem(DEFAULT_PRICE_CLOSE.doubleValue())))
            .andExpect(jsonPath("$.[*].priceVolume").value(hasItem(DEFAULT_PRICE_VOLUME.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPriceDataHistory() throws Exception {
        // Initialize the database
        priceDataHistoryRepository.saveAndFlush(priceDataHistory);

        // Get the priceDataHistory
        restPriceDataHistoryMockMvc.perform(get("/api/price-data-histories/{id}", priceDataHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(priceDataHistory.getId().intValue()))
            .andExpect(jsonPath("$.priceTimeframe").value(DEFAULT_PRICE_TIMEFRAME.toString()))
            .andExpect(jsonPath("$.priceDate").value(DEFAULT_PRICE_DATE.toString()))
            .andExpect(jsonPath("$.priceTime").value(sameInstant(DEFAULT_PRICE_TIME)))
            .andExpect(jsonPath("$.priceOpen").value(DEFAULT_PRICE_OPEN.doubleValue()))
            .andExpect(jsonPath("$.priceHigh").value(DEFAULT_PRICE_HIGH.doubleValue()))
            .andExpect(jsonPath("$.priceLow").value(DEFAULT_PRICE_LOW.doubleValue()))
            .andExpect(jsonPath("$.priceClose").value(DEFAULT_PRICE_CLOSE.doubleValue()))
            .andExpect(jsonPath("$.priceVolume").value(DEFAULT_PRICE_VOLUME.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPriceDataHistory() throws Exception {
        // Get the priceDataHistory
        restPriceDataHistoryMockMvc.perform(get("/api/price-data-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceDataHistory() throws Exception {
        // Initialize the database
        priceDataHistoryService.save(priceDataHistory);

        int databaseSizeBeforeUpdate = priceDataHistoryRepository.findAll().size();

        // Update the priceDataHistory
        PriceDataHistory updatedPriceDataHistory = priceDataHistoryRepository.findById(priceDataHistory.getId()).get();
        // Disconnect from session so that the updates on updatedPriceDataHistory are not directly saved in db
        em.detach(updatedPriceDataHistory);
        updatedPriceDataHistory
            .priceTimeframe(UPDATED_PRICE_TIMEFRAME)
            .priceDate(UPDATED_PRICE_DATE)
            .priceTime(UPDATED_PRICE_TIME)
            .priceOpen(UPDATED_PRICE_OPEN)
            .priceHigh(UPDATED_PRICE_HIGH)
            .priceLow(UPDATED_PRICE_LOW)
            .priceClose(UPDATED_PRICE_CLOSE)
            .priceVolume(UPDATED_PRICE_VOLUME);

        restPriceDataHistoryMockMvc.perform(put("/api/price-data-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPriceDataHistory)))
            .andExpect(status().isOk());

        // Validate the PriceDataHistory in the database
        List<PriceDataHistory> priceDataHistoryList = priceDataHistoryRepository.findAll();
        assertThat(priceDataHistoryList).hasSize(databaseSizeBeforeUpdate);
        PriceDataHistory testPriceDataHistory = priceDataHistoryList.get(priceDataHistoryList.size() - 1);
        assertThat(testPriceDataHistory.getPriceTimeframe()).isEqualTo(UPDATED_PRICE_TIMEFRAME);
        assertThat(testPriceDataHistory.getPriceDate()).isEqualTo(UPDATED_PRICE_DATE);
        assertThat(testPriceDataHistory.getPriceTime()).isEqualTo(UPDATED_PRICE_TIME);
        assertThat(testPriceDataHistory.getPriceOpen()).isEqualTo(UPDATED_PRICE_OPEN);
        assertThat(testPriceDataHistory.getPriceHigh()).isEqualTo(UPDATED_PRICE_HIGH);
        assertThat(testPriceDataHistory.getPriceLow()).isEqualTo(UPDATED_PRICE_LOW);
        assertThat(testPriceDataHistory.getPriceClose()).isEqualTo(UPDATED_PRICE_CLOSE);
        assertThat(testPriceDataHistory.getPriceVolume()).isEqualTo(UPDATED_PRICE_VOLUME);
    }

    @Test
    @Transactional
    public void updateNonExistingPriceDataHistory() throws Exception {
        int databaseSizeBeforeUpdate = priceDataHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceDataHistoryMockMvc.perform(put("/api/price-data-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(priceDataHistory)))
            .andExpect(status().isBadRequest());

        // Validate the PriceDataHistory in the database
        List<PriceDataHistory> priceDataHistoryList = priceDataHistoryRepository.findAll();
        assertThat(priceDataHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePriceDataHistory() throws Exception {
        // Initialize the database
        priceDataHistoryService.save(priceDataHistory);

        int databaseSizeBeforeDelete = priceDataHistoryRepository.findAll().size();

        // Delete the priceDataHistory
        restPriceDataHistoryMockMvc.perform(delete("/api/price-data-histories/{id}", priceDataHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PriceDataHistory> priceDataHistoryList = priceDataHistoryRepository.findAll();
        assertThat(priceDataHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
