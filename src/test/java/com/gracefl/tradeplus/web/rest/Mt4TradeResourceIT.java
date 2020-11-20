package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.Mt4Trade;
import com.gracefl.tradeplus.repository.Mt4TradeRepository;
import com.gracefl.tradeplus.service.Mt4TradeService;

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
 * Integration tests for the {@link Mt4TradeResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class Mt4TradeResourceIT {

    private static final BigDecimal DEFAULT_TICKET = new BigDecimal(1);
    private static final BigDecimal UPDATED_TICKET = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_OPEN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_OPEN_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DIRECTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTION_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_POSITION_SIZE = 1D;
    private static final Double UPDATED_POSITION_SIZE = 2D;

    private static final Double DEFAULT_OPEN_PRICE = 1D;
    private static final Double UPDATED_OPEN_PRICE = 2D;

    private static final Double DEFAULT_STOP_LOSS_PRICE = 1D;
    private static final Double UPDATED_STOP_LOSS_PRICE = 2D;

    private static final Double DEFAULT_TAKE_PROFIT_PRICE = 1D;
    private static final Double UPDATED_TAKE_PROFIT_PRICE = 2D;

    private static final ZonedDateTime DEFAULT_CLOSE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CLOSE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_CLOSE_PRICE = 1D;
    private static final Double UPDATED_CLOSE_PRICE = 2D;

    private static final Double DEFAULT_COMMISSION = 1D;
    private static final Double UPDATED_COMMISSION = 2D;

    private static final Double DEFAULT_TAXES = 1D;
    private static final Double UPDATED_TAXES = 2D;

    private static final Double DEFAULT_SWAP = 1D;
    private static final Double UPDATED_SWAP = 2D;

    private static final Double DEFAULT_PROFIT = 1D;
    private static final Double UPDATED_PROFIT = 2D;

    @Autowired
    private Mt4TradeRepository mt4TradeRepository;

    @Autowired
    private Mt4TradeService mt4TradeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMt4TradeMockMvc;

    private Mt4Trade mt4Trade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mt4Trade createEntity(EntityManager em) {
        Mt4Trade mt4Trade = new Mt4Trade()
            .ticket(DEFAULT_TICKET)
            .openTime(DEFAULT_OPEN_TIME)
            .directionType(DEFAULT_DIRECTION_TYPE)
            .positionSize(DEFAULT_POSITION_SIZE)
            .openPrice(DEFAULT_OPEN_PRICE)
            .stopLossPrice(DEFAULT_STOP_LOSS_PRICE)
            .takeProfitPrice(DEFAULT_TAKE_PROFIT_PRICE)
            .closeTime(DEFAULT_CLOSE_TIME)
            .closePrice(DEFAULT_CLOSE_PRICE)
            .commission(DEFAULT_COMMISSION)
            .taxes(DEFAULT_TAXES)
            .swap(DEFAULT_SWAP)
            .profit(DEFAULT_PROFIT);
        return mt4Trade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mt4Trade createUpdatedEntity(EntityManager em) {
        Mt4Trade mt4Trade = new Mt4Trade()
            .ticket(UPDATED_TICKET)
            .openTime(UPDATED_OPEN_TIME)
            .directionType(UPDATED_DIRECTION_TYPE)
            .positionSize(UPDATED_POSITION_SIZE)
            .openPrice(UPDATED_OPEN_PRICE)
            .stopLossPrice(UPDATED_STOP_LOSS_PRICE)
            .takeProfitPrice(UPDATED_TAKE_PROFIT_PRICE)
            .closeTime(UPDATED_CLOSE_TIME)
            .closePrice(UPDATED_CLOSE_PRICE)
            .commission(UPDATED_COMMISSION)
            .taxes(UPDATED_TAXES)
            .swap(UPDATED_SWAP)
            .profit(UPDATED_PROFIT);
        return mt4Trade;
    }

    @BeforeEach
    public void initTest() {
        mt4Trade = createEntity(em);
    }

    @Test
    @Transactional
    public void createMt4Trade() throws Exception {
        int databaseSizeBeforeCreate = mt4TradeRepository.findAll().size();
        // Create the Mt4Trade
        restMt4TradeMockMvc.perform(post("/api/mt-4-trades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mt4Trade)))
            .andExpect(status().isCreated());

        // Validate the Mt4Trade in the database
        List<Mt4Trade> mt4TradeList = mt4TradeRepository.findAll();
        assertThat(mt4TradeList).hasSize(databaseSizeBeforeCreate + 1);
        Mt4Trade testMt4Trade = mt4TradeList.get(mt4TradeList.size() - 1);
        assertThat(testMt4Trade.getTicket()).isEqualTo(DEFAULT_TICKET);
        assertThat(testMt4Trade.getOpenTime()).isEqualTo(DEFAULT_OPEN_TIME);
        assertThat(testMt4Trade.getDirectionType()).isEqualTo(DEFAULT_DIRECTION_TYPE);
        assertThat(testMt4Trade.getPositionSize()).isEqualTo(DEFAULT_POSITION_SIZE);
        assertThat(testMt4Trade.getOpenPrice()).isEqualTo(DEFAULT_OPEN_PRICE);
        assertThat(testMt4Trade.getStopLossPrice()).isEqualTo(DEFAULT_STOP_LOSS_PRICE);
        assertThat(testMt4Trade.getTakeProfitPrice()).isEqualTo(DEFAULT_TAKE_PROFIT_PRICE);
        assertThat(testMt4Trade.getCloseTime()).isEqualTo(DEFAULT_CLOSE_TIME);
        assertThat(testMt4Trade.getClosePrice()).isEqualTo(DEFAULT_CLOSE_PRICE);
        assertThat(testMt4Trade.getCommission()).isEqualTo(DEFAULT_COMMISSION);
        assertThat(testMt4Trade.getTaxes()).isEqualTo(DEFAULT_TAXES);
        assertThat(testMt4Trade.getSwap()).isEqualTo(DEFAULT_SWAP);
        assertThat(testMt4Trade.getProfit()).isEqualTo(DEFAULT_PROFIT);
    }

    @Test
    @Transactional
    public void createMt4TradeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mt4TradeRepository.findAll().size();

        // Create the Mt4Trade with an existing ID
        mt4Trade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMt4TradeMockMvc.perform(post("/api/mt-4-trades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mt4Trade)))
            .andExpect(status().isBadRequest());

        // Validate the Mt4Trade in the database
        List<Mt4Trade> mt4TradeList = mt4TradeRepository.findAll();
        assertThat(mt4TradeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTicketIsRequired() throws Exception {
        int databaseSizeBeforeTest = mt4TradeRepository.findAll().size();
        // set the field null
        mt4Trade.setTicket(null);

        // Create the Mt4Trade, which fails.


        restMt4TradeMockMvc.perform(post("/api/mt-4-trades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mt4Trade)))
            .andExpect(status().isBadRequest());

        List<Mt4Trade> mt4TradeList = mt4TradeRepository.findAll();
        assertThat(mt4TradeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMt4Trades() throws Exception {
        // Initialize the database
        mt4TradeRepository.saveAndFlush(mt4Trade);

        // Get all the mt4TradeList
        restMt4TradeMockMvc.perform(get("/api/mt-4-trades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mt4Trade.getId().intValue())))
            .andExpect(jsonPath("$.[*].ticket").value(hasItem(DEFAULT_TICKET.intValue())))
            .andExpect(jsonPath("$.[*].openTime").value(hasItem(sameInstant(DEFAULT_OPEN_TIME))))
            .andExpect(jsonPath("$.[*].directionType").value(hasItem(DEFAULT_DIRECTION_TYPE)))
            .andExpect(jsonPath("$.[*].positionSize").value(hasItem(DEFAULT_POSITION_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].openPrice").value(hasItem(DEFAULT_OPEN_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].stopLossPrice").value(hasItem(DEFAULT_STOP_LOSS_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].takeProfitPrice").value(hasItem(DEFAULT_TAKE_PROFIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].closeTime").value(hasItem(sameInstant(DEFAULT_CLOSE_TIME))))
            .andExpect(jsonPath("$.[*].closePrice").value(hasItem(DEFAULT_CLOSE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].taxes").value(hasItem(DEFAULT_TAXES.doubleValue())))
            .andExpect(jsonPath("$.[*].swap").value(hasItem(DEFAULT_SWAP.doubleValue())))
            .andExpect(jsonPath("$.[*].profit").value(hasItem(DEFAULT_PROFIT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getMt4Trade() throws Exception {
        // Initialize the database
        mt4TradeRepository.saveAndFlush(mt4Trade);

        // Get the mt4Trade
        restMt4TradeMockMvc.perform(get("/api/mt-4-trades/{id}", mt4Trade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mt4Trade.getId().intValue()))
            .andExpect(jsonPath("$.ticket").value(DEFAULT_TICKET.intValue()))
            .andExpect(jsonPath("$.openTime").value(sameInstant(DEFAULT_OPEN_TIME)))
            .andExpect(jsonPath("$.directionType").value(DEFAULT_DIRECTION_TYPE))
            .andExpect(jsonPath("$.positionSize").value(DEFAULT_POSITION_SIZE.doubleValue()))
            .andExpect(jsonPath("$.openPrice").value(DEFAULT_OPEN_PRICE.doubleValue()))
            .andExpect(jsonPath("$.stopLossPrice").value(DEFAULT_STOP_LOSS_PRICE.doubleValue()))
            .andExpect(jsonPath("$.takeProfitPrice").value(DEFAULT_TAKE_PROFIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.closeTime").value(sameInstant(DEFAULT_CLOSE_TIME)))
            .andExpect(jsonPath("$.closePrice").value(DEFAULT_CLOSE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.commission").value(DEFAULT_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.taxes").value(DEFAULT_TAXES.doubleValue()))
            .andExpect(jsonPath("$.swap").value(DEFAULT_SWAP.doubleValue()))
            .andExpect(jsonPath("$.profit").value(DEFAULT_PROFIT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMt4Trade() throws Exception {
        // Get the mt4Trade
        restMt4TradeMockMvc.perform(get("/api/mt-4-trades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMt4Trade() throws Exception {
        // Initialize the database
        mt4TradeService.save(mt4Trade);

        int databaseSizeBeforeUpdate = mt4TradeRepository.findAll().size();

        // Update the mt4Trade
        Mt4Trade updatedMt4Trade = mt4TradeRepository.findById(mt4Trade.getId()).get();
        // Disconnect from session so that the updates on updatedMt4Trade are not directly saved in db
        em.detach(updatedMt4Trade);
        updatedMt4Trade
            .ticket(UPDATED_TICKET)
            .openTime(UPDATED_OPEN_TIME)
            .directionType(UPDATED_DIRECTION_TYPE)
            .positionSize(UPDATED_POSITION_SIZE)
            .openPrice(UPDATED_OPEN_PRICE)
            .stopLossPrice(UPDATED_STOP_LOSS_PRICE)
            .takeProfitPrice(UPDATED_TAKE_PROFIT_PRICE)
            .closeTime(UPDATED_CLOSE_TIME)
            .closePrice(UPDATED_CLOSE_PRICE)
            .commission(UPDATED_COMMISSION)
            .taxes(UPDATED_TAXES)
            .swap(UPDATED_SWAP)
            .profit(UPDATED_PROFIT);

        restMt4TradeMockMvc.perform(put("/api/mt-4-trades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMt4Trade)))
            .andExpect(status().isOk());

        // Validate the Mt4Trade in the database
        List<Mt4Trade> mt4TradeList = mt4TradeRepository.findAll();
        assertThat(mt4TradeList).hasSize(databaseSizeBeforeUpdate);
        Mt4Trade testMt4Trade = mt4TradeList.get(mt4TradeList.size() - 1);
        assertThat(testMt4Trade.getTicket()).isEqualTo(UPDATED_TICKET);
        assertThat(testMt4Trade.getOpenTime()).isEqualTo(UPDATED_OPEN_TIME);
        assertThat(testMt4Trade.getDirectionType()).isEqualTo(UPDATED_DIRECTION_TYPE);
        assertThat(testMt4Trade.getPositionSize()).isEqualTo(UPDATED_POSITION_SIZE);
        assertThat(testMt4Trade.getOpenPrice()).isEqualTo(UPDATED_OPEN_PRICE);
        assertThat(testMt4Trade.getStopLossPrice()).isEqualTo(UPDATED_STOP_LOSS_PRICE);
        assertThat(testMt4Trade.getTakeProfitPrice()).isEqualTo(UPDATED_TAKE_PROFIT_PRICE);
        assertThat(testMt4Trade.getCloseTime()).isEqualTo(UPDATED_CLOSE_TIME);
        assertThat(testMt4Trade.getClosePrice()).isEqualTo(UPDATED_CLOSE_PRICE);
        assertThat(testMt4Trade.getCommission()).isEqualTo(UPDATED_COMMISSION);
        assertThat(testMt4Trade.getTaxes()).isEqualTo(UPDATED_TAXES);
        assertThat(testMt4Trade.getSwap()).isEqualTo(UPDATED_SWAP);
        assertThat(testMt4Trade.getProfit()).isEqualTo(UPDATED_PROFIT);
    }

    @Test
    @Transactional
    public void updateNonExistingMt4Trade() throws Exception {
        int databaseSizeBeforeUpdate = mt4TradeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMt4TradeMockMvc.perform(put("/api/mt-4-trades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mt4Trade)))
            .andExpect(status().isBadRequest());

        // Validate the Mt4Trade in the database
        List<Mt4Trade> mt4TradeList = mt4TradeRepository.findAll();
        assertThat(mt4TradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMt4Trade() throws Exception {
        // Initialize the database
        mt4TradeService.save(mt4Trade);

        int databaseSizeBeforeDelete = mt4TradeRepository.findAll().size();

        // Delete the mt4Trade
        restMt4TradeMockMvc.perform(delete("/api/mt-4-trades/{id}", mt4Trade.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mt4Trade> mt4TradeList = mt4TradeRepository.findAll();
        assertThat(mt4TradeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
