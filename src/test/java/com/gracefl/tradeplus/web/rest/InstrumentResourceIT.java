package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.Instrument;
import com.gracefl.tradeplus.repository.InstrumentRepository;
import com.gracefl.tradeplus.service.InstrumentService;

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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gracefl.tradeplus.domain.enumeration.DATAPROVIDER;
/**
 * Integration tests for the {@link InstrumentResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InstrumentResourceIT {

    private static final DATAPROVIDER DEFAULT_DATA_PROVIDER = DATAPROVIDER.FXPRO;
    private static final DATAPROVIDER UPDATED_DATA_PROVIDER = DATAPROVIDER.QUANDLL;

    private static final String DEFAULT_TICKER = "AAAAAAAAAA";
    private static final String UPDATED_TICKER = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final LocalDate DEFAULT_DATE_ADDED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ADDED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_INACTIVE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_INACTIVE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private InstrumentService instrumentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstrumentMockMvc;

    private Instrument instrument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instrument createEntity(EntityManager em) {
        Instrument instrument = new Instrument()
            .dataProvider(DEFAULT_DATA_PROVIDER)
            .ticker(DEFAULT_TICKER)
            .exchange(DEFAULT_EXCHANGE)
            .description(DEFAULT_DESCRIPTION)
            .dataFrom(DEFAULT_DATA_FROM)
            .isActive(DEFAULT_IS_ACTIVE)
            .dateAdded(DEFAULT_DATE_ADDED)
            .dateInactive(DEFAULT_DATE_INACTIVE);
        return instrument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instrument createUpdatedEntity(EntityManager em) {
        Instrument instrument = new Instrument()
            .dataProvider(UPDATED_DATA_PROVIDER)
            .ticker(UPDATED_TICKER)
            .exchange(UPDATED_EXCHANGE)
            .description(UPDATED_DESCRIPTION)
            .dataFrom(UPDATED_DATA_FROM)
            .isActive(UPDATED_IS_ACTIVE)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateInactive(UPDATED_DATE_INACTIVE);
        return instrument;
    }

    @BeforeEach
    public void initTest() {
        instrument = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstrument() throws Exception {
        int databaseSizeBeforeCreate = instrumentRepository.findAll().size();
        // Create the Instrument
        restInstrumentMockMvc.perform(post("/api/instruments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instrument)))
            .andExpect(status().isCreated());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeCreate + 1);
        Instrument testInstrument = instrumentList.get(instrumentList.size() - 1);
        assertThat(testInstrument.getDataProvider()).isEqualTo(DEFAULT_DATA_PROVIDER);
        assertThat(testInstrument.getTicker()).isEqualTo(DEFAULT_TICKER);
        assertThat(testInstrument.getExchange()).isEqualTo(DEFAULT_EXCHANGE);
        assertThat(testInstrument.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInstrument.getDataFrom()).isEqualTo(DEFAULT_DATA_FROM);
        assertThat(testInstrument.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testInstrument.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testInstrument.getDateInactive()).isEqualTo(DEFAULT_DATE_INACTIVE);
    }

    @Test
    @Transactional
    public void createInstrumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instrumentRepository.findAll().size();

        // Create the Instrument with an existing ID
        instrument.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstrumentMockMvc.perform(post("/api/instruments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instrument)))
            .andExpect(status().isBadRequest());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTickerIsRequired() throws Exception {
        int databaseSizeBeforeTest = instrumentRepository.findAll().size();
        // set the field null
        instrument.setTicker(null);

        // Create the Instrument, which fails.


        restInstrumentMockMvc.perform(post("/api/instruments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instrument)))
            .andExpect(status().isBadRequest());

        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstruments() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        // Get all the instrumentList
        restInstrumentMockMvc.perform(get("/api/instruments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instrument.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataProvider").value(hasItem(DEFAULT_DATA_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].ticker").value(hasItem(DEFAULT_TICKER)))
            .andExpect(jsonPath("$.[*].exchange").value(hasItem(DEFAULT_EXCHANGE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dataFrom").value(hasItem(DEFAULT_DATA_FROM.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].dateInactive").value(hasItem(DEFAULT_DATE_INACTIVE.toString())));
    }
    
    @Test
    @Transactional
    public void getInstrument() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        // Get the instrument
        restInstrumentMockMvc.perform(get("/api/instruments/{id}", instrument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instrument.getId().intValue()))
            .andExpect(jsonPath("$.dataProvider").value(DEFAULT_DATA_PROVIDER.toString()))
            .andExpect(jsonPath("$.ticker").value(DEFAULT_TICKER))
            .andExpect(jsonPath("$.exchange").value(DEFAULT_EXCHANGE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dataFrom").value(DEFAULT_DATA_FROM.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED.toString()))
            .andExpect(jsonPath("$.dateInactive").value(DEFAULT_DATE_INACTIVE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInstrument() throws Exception {
        // Get the instrument
        restInstrumentMockMvc.perform(get("/api/instruments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstrument() throws Exception {
        // Initialize the database
        instrumentService.save(instrument);

        int databaseSizeBeforeUpdate = instrumentRepository.findAll().size();

        // Update the instrument
        Instrument updatedInstrument = instrumentRepository.findById(instrument.getId()).get();
        // Disconnect from session so that the updates on updatedInstrument are not directly saved in db
        em.detach(updatedInstrument);
        updatedInstrument
            .dataProvider(UPDATED_DATA_PROVIDER)
            .ticker(UPDATED_TICKER)
            .exchange(UPDATED_EXCHANGE)
            .description(UPDATED_DESCRIPTION)
            .dataFrom(UPDATED_DATA_FROM)
            .isActive(UPDATED_IS_ACTIVE)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateInactive(UPDATED_DATE_INACTIVE);

        restInstrumentMockMvc.perform(put("/api/instruments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstrument)))
            .andExpect(status().isOk());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeUpdate);
        Instrument testInstrument = instrumentList.get(instrumentList.size() - 1);
        assertThat(testInstrument.getDataProvider()).isEqualTo(UPDATED_DATA_PROVIDER);
        assertThat(testInstrument.getTicker()).isEqualTo(UPDATED_TICKER);
        assertThat(testInstrument.getExchange()).isEqualTo(UPDATED_EXCHANGE);
        assertThat(testInstrument.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInstrument.getDataFrom()).isEqualTo(UPDATED_DATA_FROM);
        assertThat(testInstrument.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testInstrument.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testInstrument.getDateInactive()).isEqualTo(UPDATED_DATE_INACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingInstrument() throws Exception {
        int databaseSizeBeforeUpdate = instrumentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstrumentMockMvc.perform(put("/api/instruments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instrument)))
            .andExpect(status().isBadRequest());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstrument() throws Exception {
        // Initialize the database
        instrumentService.save(instrument);

        int databaseSizeBeforeDelete = instrumentRepository.findAll().size();

        // Delete the instrument
        restInstrumentMockMvc.perform(delete("/api/instruments/{id}", instrument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
