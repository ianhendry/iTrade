package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.SiteAccount;
import com.gracefl.tradeplus.repository.SiteAccountRepository;
import com.gracefl.tradeplus.service.SiteAccountService;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SiteAccountResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SiteAccountResourceIT {

    private static final String DEFAULT_ACCOUNT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACCOUNT_REAL = false;
    private static final Boolean UPDATED_ACCOUNT_REAL = true;

    private static final BigDecimal DEFAULT_ACCOUNT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACCOUNT_BALANCE = new BigDecimal(2);

    private static final LocalDate DEFAULT_ACCOUNT_OPEN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCOUNT_OPEN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACCOUNT_CLOSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCOUNT_CLOSE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SiteAccountRepository siteAccountRepository;

    @Autowired
    private SiteAccountService siteAccountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteAccountMockMvc;

    private SiteAccount siteAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteAccount createEntity(EntityManager em) {
        SiteAccount siteAccount = new SiteAccount()
            .accountEmail(DEFAULT_ACCOUNT_EMAIL)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accountReal(DEFAULT_ACCOUNT_REAL)
            .accountBalance(DEFAULT_ACCOUNT_BALANCE)
            .accountOpenDate(DEFAULT_ACCOUNT_OPEN_DATE)
            .accountCloseDate(DEFAULT_ACCOUNT_CLOSE_DATE);
        return siteAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteAccount createUpdatedEntity(EntityManager em) {
        SiteAccount siteAccount = new SiteAccount()
            .accountEmail(UPDATED_ACCOUNT_EMAIL)
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountReal(UPDATED_ACCOUNT_REAL)
            .accountBalance(UPDATED_ACCOUNT_BALANCE)
            .accountOpenDate(UPDATED_ACCOUNT_OPEN_DATE)
            .accountCloseDate(UPDATED_ACCOUNT_CLOSE_DATE);
        return siteAccount;
    }

    @BeforeEach
    public void initTest() {
        siteAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createSiteAccount() throws Exception {
        int databaseSizeBeforeCreate = siteAccountRepository.findAll().size();
        // Create the SiteAccount
        restSiteAccountMockMvc.perform(post("/api/site-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteAccount)))
            .andExpect(status().isCreated());

        // Validate the SiteAccount in the database
        List<SiteAccount> siteAccountList = siteAccountRepository.findAll();
        assertThat(siteAccountList).hasSize(databaseSizeBeforeCreate + 1);
        SiteAccount testSiteAccount = siteAccountList.get(siteAccountList.size() - 1);
        assertThat(testSiteAccount.getAccountEmail()).isEqualTo(DEFAULT_ACCOUNT_EMAIL);
        assertThat(testSiteAccount.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testSiteAccount.isAccountReal()).isEqualTo(DEFAULT_ACCOUNT_REAL);
        assertThat(testSiteAccount.getAccountBalance()).isEqualTo(DEFAULT_ACCOUNT_BALANCE);
        assertThat(testSiteAccount.getAccountOpenDate()).isEqualTo(DEFAULT_ACCOUNT_OPEN_DATE);
        assertThat(testSiteAccount.getAccountCloseDate()).isEqualTo(DEFAULT_ACCOUNT_CLOSE_DATE);
    }

    @Test
    @Transactional
    public void createSiteAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siteAccountRepository.findAll().size();

        // Create the SiteAccount with an existing ID
        siteAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteAccountMockMvc.perform(post("/api/site-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteAccount)))
            .andExpect(status().isBadRequest());

        // Validate the SiteAccount in the database
        List<SiteAccount> siteAccountList = siteAccountRepository.findAll();
        assertThat(siteAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSiteAccounts() throws Exception {
        // Initialize the database
        siteAccountRepository.saveAndFlush(siteAccount);

        // Get all the siteAccountList
        restSiteAccountMockMvc.perform(get("/api/site-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountEmail").value(hasItem(DEFAULT_ACCOUNT_EMAIL)))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].accountReal").value(hasItem(DEFAULT_ACCOUNT_REAL.booleanValue())))
            .andExpect(jsonPath("$.[*].accountBalance").value(hasItem(DEFAULT_ACCOUNT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].accountOpenDate").value(hasItem(DEFAULT_ACCOUNT_OPEN_DATE.toString())))
            .andExpect(jsonPath("$.[*].accountCloseDate").value(hasItem(DEFAULT_ACCOUNT_CLOSE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSiteAccount() throws Exception {
        // Initialize the database
        siteAccountRepository.saveAndFlush(siteAccount);

        // Get the siteAccount
        restSiteAccountMockMvc.perform(get("/api/site-accounts/{id}", siteAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siteAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountEmail").value(DEFAULT_ACCOUNT_EMAIL))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.accountReal").value(DEFAULT_ACCOUNT_REAL.booleanValue()))
            .andExpect(jsonPath("$.accountBalance").value(DEFAULT_ACCOUNT_BALANCE.intValue()))
            .andExpect(jsonPath("$.accountOpenDate").value(DEFAULT_ACCOUNT_OPEN_DATE.toString()))
            .andExpect(jsonPath("$.accountCloseDate").value(DEFAULT_ACCOUNT_CLOSE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSiteAccount() throws Exception {
        // Get the siteAccount
        restSiteAccountMockMvc.perform(get("/api/site-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSiteAccount() throws Exception {
        // Initialize the database
        siteAccountService.save(siteAccount);

        int databaseSizeBeforeUpdate = siteAccountRepository.findAll().size();

        // Update the siteAccount
        SiteAccount updatedSiteAccount = siteAccountRepository.findById(siteAccount.getId()).get();
        // Disconnect from session so that the updates on updatedSiteAccount are not directly saved in db
        em.detach(updatedSiteAccount);
        updatedSiteAccount
            .accountEmail(UPDATED_ACCOUNT_EMAIL)
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountReal(UPDATED_ACCOUNT_REAL)
            .accountBalance(UPDATED_ACCOUNT_BALANCE)
            .accountOpenDate(UPDATED_ACCOUNT_OPEN_DATE)
            .accountCloseDate(UPDATED_ACCOUNT_CLOSE_DATE);

        restSiteAccountMockMvc.perform(put("/api/site-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSiteAccount)))
            .andExpect(status().isOk());

        // Validate the SiteAccount in the database
        List<SiteAccount> siteAccountList = siteAccountRepository.findAll();
        assertThat(siteAccountList).hasSize(databaseSizeBeforeUpdate);
        SiteAccount testSiteAccount = siteAccountList.get(siteAccountList.size() - 1);
        assertThat(testSiteAccount.getAccountEmail()).isEqualTo(UPDATED_ACCOUNT_EMAIL);
        assertThat(testSiteAccount.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testSiteAccount.isAccountReal()).isEqualTo(UPDATED_ACCOUNT_REAL);
        assertThat(testSiteAccount.getAccountBalance()).isEqualTo(UPDATED_ACCOUNT_BALANCE);
        assertThat(testSiteAccount.getAccountOpenDate()).isEqualTo(UPDATED_ACCOUNT_OPEN_DATE);
        assertThat(testSiteAccount.getAccountCloseDate()).isEqualTo(UPDATED_ACCOUNT_CLOSE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSiteAccount() throws Exception {
        int databaseSizeBeforeUpdate = siteAccountRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteAccountMockMvc.perform(put("/api/site-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteAccount)))
            .andExpect(status().isBadRequest());

        // Validate the SiteAccount in the database
        List<SiteAccount> siteAccountList = siteAccountRepository.findAll();
        assertThat(siteAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSiteAccount() throws Exception {
        // Initialize the database
        siteAccountService.save(siteAccount);

        int databaseSizeBeforeDelete = siteAccountRepository.findAll().size();

        // Delete the siteAccount
        restSiteAccountMockMvc.perform(delete("/api/site-accounts/{id}", siteAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SiteAccount> siteAccountList = siteAccountRepository.findAll();
        assertThat(siteAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
