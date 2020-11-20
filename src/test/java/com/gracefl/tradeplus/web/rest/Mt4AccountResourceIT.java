package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.Mt4Account;
import com.gracefl.tradeplus.repository.Mt4AccountRepository;
import com.gracefl.tradeplus.service.Mt4AccountService;

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

import com.gracefl.tradeplus.domain.enumeration.ACCOUNTTYPE;
import com.gracefl.tradeplus.domain.enumeration.BROKER;
/**
 * Integration tests for the {@link Mt4AccountResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class Mt4AccountResourceIT {

    private static final ACCOUNTTYPE DEFAULT_ACCOUNT_TYPE = ACCOUNTTYPE.REAL;
    private static final ACCOUNTTYPE UPDATED_ACCOUNT_TYPE = ACCOUNTTYPE.DEMO;

    private static final BROKER DEFAULT_ACCOUNT_BROKER = BROKER.FXPRO;
    private static final BROKER UPDATED_ACCOUNT_BROKER = BROKER.ALPARI;

    private static final String DEFAULT_ACCOUNT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_PASSWORD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACCOUNT_ACTIVE = false;
    private static final Boolean UPDATED_ACCOUNT_ACTIVE = true;

    private static final LocalDate DEFAULT_ACCOUNT_CLOSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCOUNT_CLOSE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private Mt4AccountRepository mt4AccountRepository;

    @Autowired
    private Mt4AccountService mt4AccountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMt4AccountMockMvc;

    private Mt4Account mt4Account;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mt4Account createEntity(EntityManager em) {
        Mt4Account mt4Account = new Mt4Account()
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .accountBroker(DEFAULT_ACCOUNT_BROKER)
            .accountLogin(DEFAULT_ACCOUNT_LOGIN)
            .accountPassword(DEFAULT_ACCOUNT_PASSWORD)
            .accountActive(DEFAULT_ACCOUNT_ACTIVE)
            .accountCloseDate(DEFAULT_ACCOUNT_CLOSE_DATE);
        return mt4Account;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mt4Account createUpdatedEntity(EntityManager em) {
        Mt4Account mt4Account = new Mt4Account()
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountBroker(UPDATED_ACCOUNT_BROKER)
            .accountLogin(UPDATED_ACCOUNT_LOGIN)
            .accountPassword(UPDATED_ACCOUNT_PASSWORD)
            .accountActive(UPDATED_ACCOUNT_ACTIVE)
            .accountCloseDate(UPDATED_ACCOUNT_CLOSE_DATE);
        return mt4Account;
    }

    @BeforeEach
    public void initTest() {
        mt4Account = createEntity(em);
    }

    @Test
    @Transactional
    public void createMt4Account() throws Exception {
        int databaseSizeBeforeCreate = mt4AccountRepository.findAll().size();
        // Create the Mt4Account
        restMt4AccountMockMvc.perform(post("/api/mt-4-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mt4Account)))
            .andExpect(status().isCreated());

        // Validate the Mt4Account in the database
        List<Mt4Account> mt4AccountList = mt4AccountRepository.findAll();
        assertThat(mt4AccountList).hasSize(databaseSizeBeforeCreate + 1);
        Mt4Account testMt4Account = mt4AccountList.get(mt4AccountList.size() - 1);
        assertThat(testMt4Account.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testMt4Account.getAccountBroker()).isEqualTo(DEFAULT_ACCOUNT_BROKER);
        assertThat(testMt4Account.getAccountLogin()).isEqualTo(DEFAULT_ACCOUNT_LOGIN);
        assertThat(testMt4Account.getAccountPassword()).isEqualTo(DEFAULT_ACCOUNT_PASSWORD);
        assertThat(testMt4Account.isAccountActive()).isEqualTo(DEFAULT_ACCOUNT_ACTIVE);
        assertThat(testMt4Account.getAccountCloseDate()).isEqualTo(DEFAULT_ACCOUNT_CLOSE_DATE);
    }

    @Test
    @Transactional
    public void createMt4AccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mt4AccountRepository.findAll().size();

        // Create the Mt4Account with an existing ID
        mt4Account.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMt4AccountMockMvc.perform(post("/api/mt-4-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mt4Account)))
            .andExpect(status().isBadRequest());

        // Validate the Mt4Account in the database
        List<Mt4Account> mt4AccountList = mt4AccountRepository.findAll();
        assertThat(mt4AccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMt4Accounts() throws Exception {
        // Initialize the database
        mt4AccountRepository.saveAndFlush(mt4Account);

        // Get all the mt4AccountList
        restMt4AccountMockMvc.perform(get("/api/mt-4-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mt4Account.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].accountBroker").value(hasItem(DEFAULT_ACCOUNT_BROKER.toString())))
            .andExpect(jsonPath("$.[*].accountLogin").value(hasItem(DEFAULT_ACCOUNT_LOGIN)))
            .andExpect(jsonPath("$.[*].accountPassword").value(hasItem(DEFAULT_ACCOUNT_PASSWORD)))
            .andExpect(jsonPath("$.[*].accountActive").value(hasItem(DEFAULT_ACCOUNT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].accountCloseDate").value(hasItem(DEFAULT_ACCOUNT_CLOSE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getMt4Account() throws Exception {
        // Initialize the database
        mt4AccountRepository.saveAndFlush(mt4Account);

        // Get the mt4Account
        restMt4AccountMockMvc.perform(get("/api/mt-4-accounts/{id}", mt4Account.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mt4Account.getId().intValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.accountBroker").value(DEFAULT_ACCOUNT_BROKER.toString()))
            .andExpect(jsonPath("$.accountLogin").value(DEFAULT_ACCOUNT_LOGIN))
            .andExpect(jsonPath("$.accountPassword").value(DEFAULT_ACCOUNT_PASSWORD))
            .andExpect(jsonPath("$.accountActive").value(DEFAULT_ACCOUNT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.accountCloseDate").value(DEFAULT_ACCOUNT_CLOSE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMt4Account() throws Exception {
        // Get the mt4Account
        restMt4AccountMockMvc.perform(get("/api/mt-4-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMt4Account() throws Exception {
        // Initialize the database
        mt4AccountService.save(mt4Account);

        int databaseSizeBeforeUpdate = mt4AccountRepository.findAll().size();

        // Update the mt4Account
        Mt4Account updatedMt4Account = mt4AccountRepository.findById(mt4Account.getId()).get();
        // Disconnect from session so that the updates on updatedMt4Account are not directly saved in db
        em.detach(updatedMt4Account);
        updatedMt4Account
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountBroker(UPDATED_ACCOUNT_BROKER)
            .accountLogin(UPDATED_ACCOUNT_LOGIN)
            .accountPassword(UPDATED_ACCOUNT_PASSWORD)
            .accountActive(UPDATED_ACCOUNT_ACTIVE)
            .accountCloseDate(UPDATED_ACCOUNT_CLOSE_DATE);

        restMt4AccountMockMvc.perform(put("/api/mt-4-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMt4Account)))
            .andExpect(status().isOk());

        // Validate the Mt4Account in the database
        List<Mt4Account> mt4AccountList = mt4AccountRepository.findAll();
        assertThat(mt4AccountList).hasSize(databaseSizeBeforeUpdate);
        Mt4Account testMt4Account = mt4AccountList.get(mt4AccountList.size() - 1);
        assertThat(testMt4Account.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testMt4Account.getAccountBroker()).isEqualTo(UPDATED_ACCOUNT_BROKER);
        assertThat(testMt4Account.getAccountLogin()).isEqualTo(UPDATED_ACCOUNT_LOGIN);
        assertThat(testMt4Account.getAccountPassword()).isEqualTo(UPDATED_ACCOUNT_PASSWORD);
        assertThat(testMt4Account.isAccountActive()).isEqualTo(UPDATED_ACCOUNT_ACTIVE);
        assertThat(testMt4Account.getAccountCloseDate()).isEqualTo(UPDATED_ACCOUNT_CLOSE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMt4Account() throws Exception {
        int databaseSizeBeforeUpdate = mt4AccountRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMt4AccountMockMvc.perform(put("/api/mt-4-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mt4Account)))
            .andExpect(status().isBadRequest());

        // Validate the Mt4Account in the database
        List<Mt4Account> mt4AccountList = mt4AccountRepository.findAll();
        assertThat(mt4AccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMt4Account() throws Exception {
        // Initialize the database
        mt4AccountService.save(mt4Account);

        int databaseSizeBeforeDelete = mt4AccountRepository.findAll().size();

        // Delete the mt4Account
        restMt4AccountMockMvc.perform(delete("/api/mt-4-accounts/{id}", mt4Account.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mt4Account> mt4AccountList = mt4AccountRepository.findAll();
        assertThat(mt4AccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
