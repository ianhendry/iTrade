package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.TradeSignals;
import com.gracefl.tradeplus.repository.TradeSignalsRepository;
import com.gracefl.tradeplus.service.TradeSignalsService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gracefl.tradeplus.domain.enumeration.SIGNALINDICATES;
/**
 * Integration tests for the {@link TradeSignalsResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TradeSignalsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_BARS = 1;
    private static final Integer UPDATED_NUMBER_OF_BARS = 2;

    private static final SIGNALINDICATES DEFAULT_SIGNAL_INDICATES = SIGNALINDICATES.STRENGTH;
    private static final SIGNALINDICATES UPDATED_SIGNAL_INDICATES = SIGNALINDICATES.WEAKNESS;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BACKGROUND = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_TO_TAKE = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_TO_TAKE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE_NUMBER = 1;
    private static final Integer UPDATED_SEQUENCE_NUMBER = 2;

    @Autowired
    private TradeSignalsRepository tradeSignalsRepository;

    @Autowired
    private TradeSignalsService tradeSignalsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTradeSignalsMockMvc;

    private TradeSignals tradeSignals;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TradeSignals createEntity(EntityManager em) {
        TradeSignals tradeSignals = new TradeSignals()
            .name(DEFAULT_NAME)
            .note(DEFAULT_NOTE)
            .numberOfBars(DEFAULT_NUMBER_OF_BARS)
            .signalIndicates(DEFAULT_SIGNAL_INDICATES)
            .description(DEFAULT_DESCRIPTION)
            .background(DEFAULT_BACKGROUND)
            .actionToTake(DEFAULT_ACTION_TO_TAKE)
            .sequenceNumber(DEFAULT_SEQUENCE_NUMBER);
        return tradeSignals;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TradeSignals createUpdatedEntity(EntityManager em) {
        TradeSignals tradeSignals = new TradeSignals()
            .name(UPDATED_NAME)
            .note(UPDATED_NOTE)
            .numberOfBars(UPDATED_NUMBER_OF_BARS)
            .signalIndicates(UPDATED_SIGNAL_INDICATES)
            .description(UPDATED_DESCRIPTION)
            .background(UPDATED_BACKGROUND)
            .actionToTake(UPDATED_ACTION_TO_TAKE)
            .sequenceNumber(UPDATED_SEQUENCE_NUMBER);
        return tradeSignals;
    }

    @BeforeEach
    public void initTest() {
        tradeSignals = createEntity(em);
    }

    @Test
    @Transactional
    public void createTradeSignals() throws Exception {
        int databaseSizeBeforeCreate = tradeSignalsRepository.findAll().size();
        // Create the TradeSignals
        restTradeSignalsMockMvc.perform(post("/api/trade-signals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradeSignals)))
            .andExpect(status().isCreated());

        // Validate the TradeSignals in the database
        List<TradeSignals> tradeSignalsList = tradeSignalsRepository.findAll();
        assertThat(tradeSignalsList).hasSize(databaseSizeBeforeCreate + 1);
        TradeSignals testTradeSignals = tradeSignalsList.get(tradeSignalsList.size() - 1);
        assertThat(testTradeSignals.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTradeSignals.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTradeSignals.getNumberOfBars()).isEqualTo(DEFAULT_NUMBER_OF_BARS);
        assertThat(testTradeSignals.getSignalIndicates()).isEqualTo(DEFAULT_SIGNAL_INDICATES);
        assertThat(testTradeSignals.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTradeSignals.getBackground()).isEqualTo(DEFAULT_BACKGROUND);
        assertThat(testTradeSignals.getActionToTake()).isEqualTo(DEFAULT_ACTION_TO_TAKE);
        assertThat(testTradeSignals.getSequenceNumber()).isEqualTo(DEFAULT_SEQUENCE_NUMBER);
    }

    @Test
    @Transactional
    public void createTradeSignalsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tradeSignalsRepository.findAll().size();

        // Create the TradeSignals with an existing ID
        tradeSignals.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTradeSignalsMockMvc.perform(post("/api/trade-signals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradeSignals)))
            .andExpect(status().isBadRequest());

        // Validate the TradeSignals in the database
        List<TradeSignals> tradeSignalsList = tradeSignalsRepository.findAll();
        assertThat(tradeSignalsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradeSignalsRepository.findAll().size();
        // set the field null
        tradeSignals.setName(null);

        // Create the TradeSignals, which fails.


        restTradeSignalsMockMvc.perform(post("/api/trade-signals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradeSignals)))
            .andExpect(status().isBadRequest());

        List<TradeSignals> tradeSignalsList = tradeSignalsRepository.findAll();
        assertThat(tradeSignalsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTradeSignals() throws Exception {
        // Initialize the database
        tradeSignalsRepository.saveAndFlush(tradeSignals);

        // Get all the tradeSignalsList
        restTradeSignalsMockMvc.perform(get("/api/trade-signals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tradeSignals.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].numberOfBars").value(hasItem(DEFAULT_NUMBER_OF_BARS)))
            .andExpect(jsonPath("$.[*].signalIndicates").value(hasItem(DEFAULT_SIGNAL_INDICATES.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].background").value(hasItem(DEFAULT_BACKGROUND.toString())))
            .andExpect(jsonPath("$.[*].actionToTake").value(hasItem(DEFAULT_ACTION_TO_TAKE.toString())))
            .andExpect(jsonPath("$.[*].sequenceNumber").value(hasItem(DEFAULT_SEQUENCE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getTradeSignals() throws Exception {
        // Initialize the database
        tradeSignalsRepository.saveAndFlush(tradeSignals);

        // Get the tradeSignals
        restTradeSignalsMockMvc.perform(get("/api/trade-signals/{id}", tradeSignals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tradeSignals.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.numberOfBars").value(DEFAULT_NUMBER_OF_BARS))
            .andExpect(jsonPath("$.signalIndicates").value(DEFAULT_SIGNAL_INDICATES.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.background").value(DEFAULT_BACKGROUND.toString()))
            .andExpect(jsonPath("$.actionToTake").value(DEFAULT_ACTION_TO_TAKE.toString()))
            .andExpect(jsonPath("$.sequenceNumber").value(DEFAULT_SEQUENCE_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingTradeSignals() throws Exception {
        // Get the tradeSignals
        restTradeSignalsMockMvc.perform(get("/api/trade-signals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTradeSignals() throws Exception {
        // Initialize the database
        tradeSignalsService.save(tradeSignals);

        int databaseSizeBeforeUpdate = tradeSignalsRepository.findAll().size();

        // Update the tradeSignals
        TradeSignals updatedTradeSignals = tradeSignalsRepository.findById(tradeSignals.getId()).get();
        // Disconnect from session so that the updates on updatedTradeSignals are not directly saved in db
        em.detach(updatedTradeSignals);
        updatedTradeSignals
            .name(UPDATED_NAME)
            .note(UPDATED_NOTE)
            .numberOfBars(UPDATED_NUMBER_OF_BARS)
            .signalIndicates(UPDATED_SIGNAL_INDICATES)
            .description(UPDATED_DESCRIPTION)
            .background(UPDATED_BACKGROUND)
            .actionToTake(UPDATED_ACTION_TO_TAKE)
            .sequenceNumber(UPDATED_SEQUENCE_NUMBER);

        restTradeSignalsMockMvc.perform(put("/api/trade-signals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTradeSignals)))
            .andExpect(status().isOk());

        // Validate the TradeSignals in the database
        List<TradeSignals> tradeSignalsList = tradeSignalsRepository.findAll();
        assertThat(tradeSignalsList).hasSize(databaseSizeBeforeUpdate);
        TradeSignals testTradeSignals = tradeSignalsList.get(tradeSignalsList.size() - 1);
        assertThat(testTradeSignals.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTradeSignals.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTradeSignals.getNumberOfBars()).isEqualTo(UPDATED_NUMBER_OF_BARS);
        assertThat(testTradeSignals.getSignalIndicates()).isEqualTo(UPDATED_SIGNAL_INDICATES);
        assertThat(testTradeSignals.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTradeSignals.getBackground()).isEqualTo(UPDATED_BACKGROUND);
        assertThat(testTradeSignals.getActionToTake()).isEqualTo(UPDATED_ACTION_TO_TAKE);
        assertThat(testTradeSignals.getSequenceNumber()).isEqualTo(UPDATED_SEQUENCE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingTradeSignals() throws Exception {
        int databaseSizeBeforeUpdate = tradeSignalsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTradeSignalsMockMvc.perform(put("/api/trade-signals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradeSignals)))
            .andExpect(status().isBadRequest());

        // Validate the TradeSignals in the database
        List<TradeSignals> tradeSignalsList = tradeSignalsRepository.findAll();
        assertThat(tradeSignalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTradeSignals() throws Exception {
        // Initialize the database
        tradeSignalsService.save(tradeSignals);

        int databaseSizeBeforeDelete = tradeSignalsRepository.findAll().size();

        // Delete the tradeSignals
        restTradeSignalsMockMvc.perform(delete("/api/trade-signals/{id}", tradeSignals.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TradeSignals> tradeSignalsList = tradeSignalsRepository.findAll();
        assertThat(tradeSignalsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
