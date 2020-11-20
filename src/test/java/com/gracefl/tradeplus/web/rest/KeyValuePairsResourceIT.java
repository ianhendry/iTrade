package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.KeyValuePairs;
import com.gracefl.tradeplus.repository.KeyValuePairsRepository;
import com.gracefl.tradeplus.service.KeyValuePairsService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link KeyValuePairsResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class KeyValuePairsResourceIT {

    private static final String DEFAULT_KEY_VALUE_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_KEY_VALUE_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_KEY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_KEY_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_KEY_VALUE_ENTRY = "AAAAAAAAAA";
    private static final String UPDATED_KEY_VALUE_ENTRY = "BBBBBBBBBB";

    @Autowired
    private KeyValuePairsRepository keyValuePairsRepository;

    @Autowired
    private KeyValuePairsService keyValuePairsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKeyValuePairsMockMvc;

    private KeyValuePairs keyValuePairs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyValuePairs createEntity(EntityManager em) {
        KeyValuePairs keyValuePairs = new KeyValuePairs()
            .keyValueGroup(DEFAULT_KEY_VALUE_GROUP)
            .keyValue(DEFAULT_KEY_VALUE)
            .keyValueEntry(DEFAULT_KEY_VALUE_ENTRY);
        return keyValuePairs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeyValuePairs createUpdatedEntity(EntityManager em) {
        KeyValuePairs keyValuePairs = new KeyValuePairs()
            .keyValueGroup(UPDATED_KEY_VALUE_GROUP)
            .keyValue(UPDATED_KEY_VALUE)
            .keyValueEntry(UPDATED_KEY_VALUE_ENTRY);
        return keyValuePairs;
    }

    @BeforeEach
    public void initTest() {
        keyValuePairs = createEntity(em);
    }

    @Test
    @Transactional
    public void createKeyValuePairs() throws Exception {
        int databaseSizeBeforeCreate = keyValuePairsRepository.findAll().size();
        // Create the KeyValuePairs
        restKeyValuePairsMockMvc.perform(post("/api/key-value-pairs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyValuePairs)))
            .andExpect(status().isCreated());

        // Validate the KeyValuePairs in the database
        List<KeyValuePairs> keyValuePairsList = keyValuePairsRepository.findAll();
        assertThat(keyValuePairsList).hasSize(databaseSizeBeforeCreate + 1);
        KeyValuePairs testKeyValuePairs = keyValuePairsList.get(keyValuePairsList.size() - 1);
        assertThat(testKeyValuePairs.getKeyValueGroup()).isEqualTo(DEFAULT_KEY_VALUE_GROUP);
        assertThat(testKeyValuePairs.getKeyValue()).isEqualTo(DEFAULT_KEY_VALUE);
        assertThat(testKeyValuePairs.getKeyValueEntry()).isEqualTo(DEFAULT_KEY_VALUE_ENTRY);
    }

    @Test
    @Transactional
    public void createKeyValuePairsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = keyValuePairsRepository.findAll().size();

        // Create the KeyValuePairs with an existing ID
        keyValuePairs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeyValuePairsMockMvc.perform(post("/api/key-value-pairs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyValuePairs)))
            .andExpect(status().isBadRequest());

        // Validate the KeyValuePairs in the database
        List<KeyValuePairs> keyValuePairsList = keyValuePairsRepository.findAll();
        assertThat(keyValuePairsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllKeyValuePairs() throws Exception {
        // Initialize the database
        keyValuePairsRepository.saveAndFlush(keyValuePairs);

        // Get all the keyValuePairsList
        restKeyValuePairsMockMvc.perform(get("/api/key-value-pairs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyValuePairs.getId().intValue())))
            .andExpect(jsonPath("$.[*].keyValueGroup").value(hasItem(DEFAULT_KEY_VALUE_GROUP)))
            .andExpect(jsonPath("$.[*].keyValue").value(hasItem(DEFAULT_KEY_VALUE)))
            .andExpect(jsonPath("$.[*].keyValueEntry").value(hasItem(DEFAULT_KEY_VALUE_ENTRY)));
    }
    
    @Test
    @Transactional
    public void getKeyValuePairs() throws Exception {
        // Initialize the database
        keyValuePairsRepository.saveAndFlush(keyValuePairs);

        // Get the keyValuePairs
        restKeyValuePairsMockMvc.perform(get("/api/key-value-pairs/{id}", keyValuePairs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(keyValuePairs.getId().intValue()))
            .andExpect(jsonPath("$.keyValueGroup").value(DEFAULT_KEY_VALUE_GROUP))
            .andExpect(jsonPath("$.keyValue").value(DEFAULT_KEY_VALUE))
            .andExpect(jsonPath("$.keyValueEntry").value(DEFAULT_KEY_VALUE_ENTRY));
    }
    @Test
    @Transactional
    public void getNonExistingKeyValuePairs() throws Exception {
        // Get the keyValuePairs
        restKeyValuePairsMockMvc.perform(get("/api/key-value-pairs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKeyValuePairs() throws Exception {
        // Initialize the database
        keyValuePairsService.save(keyValuePairs);

        int databaseSizeBeforeUpdate = keyValuePairsRepository.findAll().size();

        // Update the keyValuePairs
        KeyValuePairs updatedKeyValuePairs = keyValuePairsRepository.findById(keyValuePairs.getId()).get();
        // Disconnect from session so that the updates on updatedKeyValuePairs are not directly saved in db
        em.detach(updatedKeyValuePairs);
        updatedKeyValuePairs
            .keyValueGroup(UPDATED_KEY_VALUE_GROUP)
            .keyValue(UPDATED_KEY_VALUE)
            .keyValueEntry(UPDATED_KEY_VALUE_ENTRY);

        restKeyValuePairsMockMvc.perform(put("/api/key-value-pairs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKeyValuePairs)))
            .andExpect(status().isOk());

        // Validate the KeyValuePairs in the database
        List<KeyValuePairs> keyValuePairsList = keyValuePairsRepository.findAll();
        assertThat(keyValuePairsList).hasSize(databaseSizeBeforeUpdate);
        KeyValuePairs testKeyValuePairs = keyValuePairsList.get(keyValuePairsList.size() - 1);
        assertThat(testKeyValuePairs.getKeyValueGroup()).isEqualTo(UPDATED_KEY_VALUE_GROUP);
        assertThat(testKeyValuePairs.getKeyValue()).isEqualTo(UPDATED_KEY_VALUE);
        assertThat(testKeyValuePairs.getKeyValueEntry()).isEqualTo(UPDATED_KEY_VALUE_ENTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingKeyValuePairs() throws Exception {
        int databaseSizeBeforeUpdate = keyValuePairsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeyValuePairsMockMvc.perform(put("/api/key-value-pairs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keyValuePairs)))
            .andExpect(status().isBadRequest());

        // Validate the KeyValuePairs in the database
        List<KeyValuePairs> keyValuePairsList = keyValuePairsRepository.findAll();
        assertThat(keyValuePairsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKeyValuePairs() throws Exception {
        // Initialize the database
        keyValuePairsService.save(keyValuePairs);

        int databaseSizeBeforeDelete = keyValuePairsRepository.findAll().size();

        // Delete the keyValuePairs
        restKeyValuePairsMockMvc.perform(delete("/api/key-value-pairs/{id}", keyValuePairs.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KeyValuePairs> keyValuePairsList = keyValuePairsRepository.findAll();
        assertThat(keyValuePairsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
