package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.SignalSequences;
import com.gracefl.tradeplus.repository.SignalSequencesRepository;
import com.gracefl.tradeplus.service.SignalSequencesService;

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

/**
 * Integration tests for the {@link SignalSequencesResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SignalSequencesResourceIT {

    private static final String DEFAULT_SEQUENCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEQUENCE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SEQUENCE_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_SEQUENCE_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_SEQUENCE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SEQUENCE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SignalSequencesRepository signalSequencesRepository;

    @Autowired
    private SignalSequencesService signalSequencesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSignalSequencesMockMvc;

    private SignalSequences signalSequences;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SignalSequences createEntity(EntityManager em) {
        SignalSequences signalSequences = new SignalSequences()
            .sequenceName(DEFAULT_SEQUENCE_NAME)
            .sequenceIdentifier(DEFAULT_SEQUENCE_IDENTIFIER)
            .sequenceDescription(DEFAULT_SEQUENCE_DESCRIPTION);
        return signalSequences;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SignalSequences createUpdatedEntity(EntityManager em) {
        SignalSequences signalSequences = new SignalSequences()
            .sequenceName(UPDATED_SEQUENCE_NAME)
            .sequenceIdentifier(UPDATED_SEQUENCE_IDENTIFIER)
            .sequenceDescription(UPDATED_SEQUENCE_DESCRIPTION);
        return signalSequences;
    }

    @BeforeEach
    public void initTest() {
        signalSequences = createEntity(em);
    }

    @Test
    @Transactional
    public void createSignalSequences() throws Exception {
        int databaseSizeBeforeCreate = signalSequencesRepository.findAll().size();
        // Create the SignalSequences
        restSignalSequencesMockMvc.perform(post("/api/signal-sequences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signalSequences)))
            .andExpect(status().isCreated());

        // Validate the SignalSequences in the database
        List<SignalSequences> signalSequencesList = signalSequencesRepository.findAll();
        assertThat(signalSequencesList).hasSize(databaseSizeBeforeCreate + 1);
        SignalSequences testSignalSequences = signalSequencesList.get(signalSequencesList.size() - 1);
        assertThat(testSignalSequences.getSequenceName()).isEqualTo(DEFAULT_SEQUENCE_NAME);
        assertThat(testSignalSequences.getSequenceIdentifier()).isEqualTo(DEFAULT_SEQUENCE_IDENTIFIER);
        assertThat(testSignalSequences.getSequenceDescription()).isEqualTo(DEFAULT_SEQUENCE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSignalSequencesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = signalSequencesRepository.findAll().size();

        // Create the SignalSequences with an existing ID
        signalSequences.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSignalSequencesMockMvc.perform(post("/api/signal-sequences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signalSequences)))
            .andExpect(status().isBadRequest());

        // Validate the SignalSequences in the database
        List<SignalSequences> signalSequencesList = signalSequencesRepository.findAll();
        assertThat(signalSequencesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSequenceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = signalSequencesRepository.findAll().size();
        // set the field null
        signalSequences.setSequenceName(null);

        // Create the SignalSequences, which fails.


        restSignalSequencesMockMvc.perform(post("/api/signal-sequences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signalSequences)))
            .andExpect(status().isBadRequest());

        List<SignalSequences> signalSequencesList = signalSequencesRepository.findAll();
        assertThat(signalSequencesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSequenceIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = signalSequencesRepository.findAll().size();
        // set the field null
        signalSequences.setSequenceIdentifier(null);

        // Create the SignalSequences, which fails.


        restSignalSequencesMockMvc.perform(post("/api/signal-sequences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signalSequences)))
            .andExpect(status().isBadRequest());

        List<SignalSequences> signalSequencesList = signalSequencesRepository.findAll();
        assertThat(signalSequencesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSignalSequences() throws Exception {
        // Initialize the database
        signalSequencesRepository.saveAndFlush(signalSequences);

        // Get all the signalSequencesList
        restSignalSequencesMockMvc.perform(get("/api/signal-sequences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(signalSequences.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceName").value(hasItem(DEFAULT_SEQUENCE_NAME)))
            .andExpect(jsonPath("$.[*].sequenceIdentifier").value(hasItem(DEFAULT_SEQUENCE_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].sequenceDescription").value(hasItem(DEFAULT_SEQUENCE_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSignalSequences() throws Exception {
        // Initialize the database
        signalSequencesRepository.saveAndFlush(signalSequences);

        // Get the signalSequences
        restSignalSequencesMockMvc.perform(get("/api/signal-sequences/{id}", signalSequences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(signalSequences.getId().intValue()))
            .andExpect(jsonPath("$.sequenceName").value(DEFAULT_SEQUENCE_NAME))
            .andExpect(jsonPath("$.sequenceIdentifier").value(DEFAULT_SEQUENCE_IDENTIFIER))
            .andExpect(jsonPath("$.sequenceDescription").value(DEFAULT_SEQUENCE_DESCRIPTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSignalSequences() throws Exception {
        // Get the signalSequences
        restSignalSequencesMockMvc.perform(get("/api/signal-sequences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSignalSequences() throws Exception {
        // Initialize the database
        signalSequencesService.save(signalSequences);

        int databaseSizeBeforeUpdate = signalSequencesRepository.findAll().size();

        // Update the signalSequences
        SignalSequences updatedSignalSequences = signalSequencesRepository.findById(signalSequences.getId()).get();
        // Disconnect from session so that the updates on updatedSignalSequences are not directly saved in db
        em.detach(updatedSignalSequences);
        updatedSignalSequences
            .sequenceName(UPDATED_SEQUENCE_NAME)
            .sequenceIdentifier(UPDATED_SEQUENCE_IDENTIFIER)
            .sequenceDescription(UPDATED_SEQUENCE_DESCRIPTION);

        restSignalSequencesMockMvc.perform(put("/api/signal-sequences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSignalSequences)))
            .andExpect(status().isOk());

        // Validate the SignalSequences in the database
        List<SignalSequences> signalSequencesList = signalSequencesRepository.findAll();
        assertThat(signalSequencesList).hasSize(databaseSizeBeforeUpdate);
        SignalSequences testSignalSequences = signalSequencesList.get(signalSequencesList.size() - 1);
        assertThat(testSignalSequences.getSequenceName()).isEqualTo(UPDATED_SEQUENCE_NAME);
        assertThat(testSignalSequences.getSequenceIdentifier()).isEqualTo(UPDATED_SEQUENCE_IDENTIFIER);
        assertThat(testSignalSequences.getSequenceDescription()).isEqualTo(UPDATED_SEQUENCE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSignalSequences() throws Exception {
        int databaseSizeBeforeUpdate = signalSequencesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSignalSequencesMockMvc.perform(put("/api/signal-sequences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signalSequences)))
            .andExpect(status().isBadRequest());

        // Validate the SignalSequences in the database
        List<SignalSequences> signalSequencesList = signalSequencesRepository.findAll();
        assertThat(signalSequencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSignalSequences() throws Exception {
        // Initialize the database
        signalSequencesService.save(signalSequences);

        int databaseSizeBeforeDelete = signalSequencesRepository.findAll().size();

        // Delete the signalSequences
        restSignalSequencesMockMvc.perform(delete("/api/signal-sequences/{id}", signalSequences.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SignalSequences> signalSequencesList = signalSequencesRepository.findAll();
        assertThat(signalSequencesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
