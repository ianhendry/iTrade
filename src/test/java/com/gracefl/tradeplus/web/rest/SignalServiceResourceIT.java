package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.SignalService;
import com.gracefl.tradeplus.repository.SignalServiceRepository;
import com.gracefl.tradeplus.service.SignalServiceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.gracefl.tradeplus.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gracefl.tradeplus.domain.enumeration.SIGNALINDICATES;
import com.gracefl.tradeplus.domain.enumeration.TIMEFRAME;
import com.gracefl.tradeplus.domain.enumeration.SIGNALBARSIZE;
import com.gracefl.tradeplus.domain.enumeration.BARCLOSE;
/**
 * Integration tests for the {@link SignalServiceResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SignalServiceResourceIT {

    private static final LocalDate DEFAULT_ALERT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ALERT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_ALERT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ALERT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ALERT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ALERT_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_ALERT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ALERT_DESCRIPTION = "BBBBBBBBBB";

    private static final SIGNALINDICATES DEFAULT_SIGNAL_INDICATES = SIGNALINDICATES.STRENGTH;
    private static final SIGNALINDICATES UPDATED_SIGNAL_INDICATES = SIGNALINDICATES.WEAKNESS;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final TIMEFRAME DEFAULT_TIMEFRAME = TIMEFRAME.M1;
    private static final TIMEFRAME UPDATED_TIMEFRAME = TIMEFRAME.M5;

    private static final Double DEFAULT_ALERT_PRICE = 1D;
    private static final Double UPDATED_ALERT_PRICE = 2D;

    private static final Boolean DEFAULT_IS_SEQUENCE_PRESENT = false;
    private static final Boolean UPDATED_IS_SEQUENCE_PRESENT = true;

    private static final BigDecimal DEFAULT_BAR_VOLUME = new BigDecimal(1);
    private static final BigDecimal UPDATED_BAR_VOLUME = new BigDecimal(2);

    private static final SIGNALBARSIZE DEFAULT_BAR_SIZE = SIGNALBARSIZE.SMALL;
    private static final SIGNALBARSIZE UPDATED_BAR_SIZE = SIGNALBARSIZE.MEDIUM;

    private static final BARCLOSE DEFAULT_BAR_CLOSE = BARCLOSE.MIDDLE;
    private static final BARCLOSE UPDATED_BAR_CLOSE = BARCLOSE.TOP;

    private static final Boolean DEFAULT_IS_PUBLISHED = false;
    private static final Boolean UPDATED_IS_PUBLISHED = true;

    @Autowired
    private SignalServiceRepository signalServiceRepository;

    @Mock
    private SignalServiceRepository signalServiceRepositoryMock;

    @Mock
    private SignalServiceService signalServiceServiceMock;

    @Autowired
    private SignalServiceService signalServiceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSignalServiceMockMvc;

    private SignalService signalService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SignalService createEntity(EntityManager em) {
        SignalService signalService = new SignalService()
            .alertDate(DEFAULT_ALERT_DATE)
            .alertTime(DEFAULT_ALERT_TIME)
            .alertText(DEFAULT_ALERT_TEXT)
            .alertDescription(DEFAULT_ALERT_DESCRIPTION)
            .signalIndicates(DEFAULT_SIGNAL_INDICATES)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .timeframe(DEFAULT_TIMEFRAME)
            .alertPrice(DEFAULT_ALERT_PRICE)
            .isSequencePresent(DEFAULT_IS_SEQUENCE_PRESENT)
            .barVolume(DEFAULT_BAR_VOLUME)
            .barSize(DEFAULT_BAR_SIZE)
            .barClose(DEFAULT_BAR_CLOSE)
            .isPublished(DEFAULT_IS_PUBLISHED);
        return signalService;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SignalService createUpdatedEntity(EntityManager em) {
        SignalService signalService = new SignalService()
            .alertDate(UPDATED_ALERT_DATE)
            .alertTime(UPDATED_ALERT_TIME)
            .alertText(UPDATED_ALERT_TEXT)
            .alertDescription(UPDATED_ALERT_DESCRIPTION)
            .signalIndicates(UPDATED_SIGNAL_INDICATES)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .timeframe(UPDATED_TIMEFRAME)
            .alertPrice(UPDATED_ALERT_PRICE)
            .isSequencePresent(UPDATED_IS_SEQUENCE_PRESENT)
            .barVolume(UPDATED_BAR_VOLUME)
            .barSize(UPDATED_BAR_SIZE)
            .barClose(UPDATED_BAR_CLOSE)
            .isPublished(UPDATED_IS_PUBLISHED);
        return signalService;
    }

    @BeforeEach
    public void initTest() {
        signalService = createEntity(em);
    }

    @Test
    @Transactional
    public void createSignalService() throws Exception {
        int databaseSizeBeforeCreate = signalServiceRepository.findAll().size();
        // Create the SignalService
        restSignalServiceMockMvc.perform(post("/api/signal-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signalService)))
            .andExpect(status().isCreated());

        // Validate the SignalService in the database
        List<SignalService> signalServiceList = signalServiceRepository.findAll();
        assertThat(signalServiceList).hasSize(databaseSizeBeforeCreate + 1);
        SignalService testSignalService = signalServiceList.get(signalServiceList.size() - 1);
        assertThat(testSignalService.getAlertDate()).isEqualTo(DEFAULT_ALERT_DATE);
        assertThat(testSignalService.getAlertTime()).isEqualTo(DEFAULT_ALERT_TIME);
        assertThat(testSignalService.getAlertText()).isEqualTo(DEFAULT_ALERT_TEXT);
        assertThat(testSignalService.getAlertDescription()).isEqualTo(DEFAULT_ALERT_DESCRIPTION);
        assertThat(testSignalService.getSignalIndicates()).isEqualTo(DEFAULT_SIGNAL_INDICATES);
        assertThat(testSignalService.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSignalService.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testSignalService.getTimeframe()).isEqualTo(DEFAULT_TIMEFRAME);
        assertThat(testSignalService.getAlertPrice()).isEqualTo(DEFAULT_ALERT_PRICE);
        assertThat(testSignalService.isIsSequencePresent()).isEqualTo(DEFAULT_IS_SEQUENCE_PRESENT);
        assertThat(testSignalService.getBarVolume()).isEqualTo(DEFAULT_BAR_VOLUME);
        assertThat(testSignalService.getBarSize()).isEqualTo(DEFAULT_BAR_SIZE);
        assertThat(testSignalService.getBarClose()).isEqualTo(DEFAULT_BAR_CLOSE);
        assertThat(testSignalService.isIsPublished()).isEqualTo(DEFAULT_IS_PUBLISHED);
    }

    @Test
    @Transactional
    public void createSignalServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = signalServiceRepository.findAll().size();

        // Create the SignalService with an existing ID
        signalService.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSignalServiceMockMvc.perform(post("/api/signal-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signalService)))
            .andExpect(status().isBadRequest());

        // Validate the SignalService in the database
        List<SignalService> signalServiceList = signalServiceRepository.findAll();
        assertThat(signalServiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAlertDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = signalServiceRepository.findAll().size();
        // set the field null
        signalService.setAlertDate(null);

        // Create the SignalService, which fails.


        restSignalServiceMockMvc.perform(post("/api/signal-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signalService)))
            .andExpect(status().isBadRequest());

        List<SignalService> signalServiceList = signalServiceRepository.findAll();
        assertThat(signalServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeframeIsRequired() throws Exception {
        int databaseSizeBeforeTest = signalServiceRepository.findAll().size();
        // set the field null
        signalService.setTimeframe(null);

        // Create the SignalService, which fails.


        restSignalServiceMockMvc.perform(post("/api/signal-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signalService)))
            .andExpect(status().isBadRequest());

        List<SignalService> signalServiceList = signalServiceRepository.findAll();
        assertThat(signalServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSignalServices() throws Exception {
        // Initialize the database
        signalServiceRepository.saveAndFlush(signalService);

        // Get all the signalServiceList
        restSignalServiceMockMvc.perform(get("/api/signal-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(signalService.getId().intValue())))
            .andExpect(jsonPath("$.[*].alertDate").value(hasItem(DEFAULT_ALERT_DATE.toString())))
            .andExpect(jsonPath("$.[*].alertTime").value(hasItem(sameInstant(DEFAULT_ALERT_TIME))))
            .andExpect(jsonPath("$.[*].alertText").value(hasItem(DEFAULT_ALERT_TEXT)))
            .andExpect(jsonPath("$.[*].alertDescription").value(hasItem(DEFAULT_ALERT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].signalIndicates").value(hasItem(DEFAULT_SIGNAL_INDICATES.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].timeframe").value(hasItem(DEFAULT_TIMEFRAME.toString())))
            .andExpect(jsonPath("$.[*].alertPrice").value(hasItem(DEFAULT_ALERT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].isSequencePresent").value(hasItem(DEFAULT_IS_SEQUENCE_PRESENT.booleanValue())))
            .andExpect(jsonPath("$.[*].barVolume").value(hasItem(DEFAULT_BAR_VOLUME.intValue())))
            .andExpect(jsonPath("$.[*].barSize").value(hasItem(DEFAULT_BAR_SIZE.toString())))
            .andExpect(jsonPath("$.[*].barClose").value(hasItem(DEFAULT_BAR_CLOSE.toString())))
            .andExpect(jsonPath("$.[*].isPublished").value(hasItem(DEFAULT_IS_PUBLISHED.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSignalServicesWithEagerRelationshipsIsEnabled() throws Exception {
        when(signalServiceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSignalServiceMockMvc.perform(get("/api/signal-services?eagerload=true"))
            .andExpect(status().isOk());

        verify(signalServiceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSignalServicesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(signalServiceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSignalServiceMockMvc.perform(get("/api/signal-services?eagerload=true"))
            .andExpect(status().isOk());

        verify(signalServiceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSignalService() throws Exception {
        // Initialize the database
        signalServiceRepository.saveAndFlush(signalService);

        // Get the signalService
        restSignalServiceMockMvc.perform(get("/api/signal-services/{id}", signalService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(signalService.getId().intValue()))
            .andExpect(jsonPath("$.alertDate").value(DEFAULT_ALERT_DATE.toString()))
            .andExpect(jsonPath("$.alertTime").value(sameInstant(DEFAULT_ALERT_TIME)))
            .andExpect(jsonPath("$.alertText").value(DEFAULT_ALERT_TEXT))
            .andExpect(jsonPath("$.alertDescription").value(DEFAULT_ALERT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.signalIndicates").value(DEFAULT_SIGNAL_INDICATES.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.timeframe").value(DEFAULT_TIMEFRAME.toString()))
            .andExpect(jsonPath("$.alertPrice").value(DEFAULT_ALERT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.isSequencePresent").value(DEFAULT_IS_SEQUENCE_PRESENT.booleanValue()))
            .andExpect(jsonPath("$.barVolume").value(DEFAULT_BAR_VOLUME.intValue()))
            .andExpect(jsonPath("$.barSize").value(DEFAULT_BAR_SIZE.toString()))
            .andExpect(jsonPath("$.barClose").value(DEFAULT_BAR_CLOSE.toString()))
            .andExpect(jsonPath("$.isPublished").value(DEFAULT_IS_PUBLISHED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSignalService() throws Exception {
        // Get the signalService
        restSignalServiceMockMvc.perform(get("/api/signal-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSignalService() throws Exception {
        // Initialize the database
        signalServiceService.save(signalService);

        int databaseSizeBeforeUpdate = signalServiceRepository.findAll().size();

        // Update the signalService
        SignalService updatedSignalService = signalServiceRepository.findById(signalService.getId()).get();
        // Disconnect from session so that the updates on updatedSignalService are not directly saved in db
        em.detach(updatedSignalService);
        updatedSignalService
            .alertDate(UPDATED_ALERT_DATE)
            .alertTime(UPDATED_ALERT_TIME)
            .alertText(UPDATED_ALERT_TEXT)
            .alertDescription(UPDATED_ALERT_DESCRIPTION)
            .signalIndicates(UPDATED_SIGNAL_INDICATES)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .timeframe(UPDATED_TIMEFRAME)
            .alertPrice(UPDATED_ALERT_PRICE)
            .isSequencePresent(UPDATED_IS_SEQUENCE_PRESENT)
            .barVolume(UPDATED_BAR_VOLUME)
            .barSize(UPDATED_BAR_SIZE)
            .barClose(UPDATED_BAR_CLOSE)
            .isPublished(UPDATED_IS_PUBLISHED);

        restSignalServiceMockMvc.perform(put("/api/signal-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSignalService)))
            .andExpect(status().isOk());

        // Validate the SignalService in the database
        List<SignalService> signalServiceList = signalServiceRepository.findAll();
        assertThat(signalServiceList).hasSize(databaseSizeBeforeUpdate);
        SignalService testSignalService = signalServiceList.get(signalServiceList.size() - 1);
        assertThat(testSignalService.getAlertDate()).isEqualTo(UPDATED_ALERT_DATE);
        assertThat(testSignalService.getAlertTime()).isEqualTo(UPDATED_ALERT_TIME);
        assertThat(testSignalService.getAlertText()).isEqualTo(UPDATED_ALERT_TEXT);
        assertThat(testSignalService.getAlertDescription()).isEqualTo(UPDATED_ALERT_DESCRIPTION);
        assertThat(testSignalService.getSignalIndicates()).isEqualTo(UPDATED_SIGNAL_INDICATES);
        assertThat(testSignalService.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSignalService.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testSignalService.getTimeframe()).isEqualTo(UPDATED_TIMEFRAME);
        assertThat(testSignalService.getAlertPrice()).isEqualTo(UPDATED_ALERT_PRICE);
        assertThat(testSignalService.isIsSequencePresent()).isEqualTo(UPDATED_IS_SEQUENCE_PRESENT);
        assertThat(testSignalService.getBarVolume()).isEqualTo(UPDATED_BAR_VOLUME);
        assertThat(testSignalService.getBarSize()).isEqualTo(UPDATED_BAR_SIZE);
        assertThat(testSignalService.getBarClose()).isEqualTo(UPDATED_BAR_CLOSE);
        assertThat(testSignalService.isIsPublished()).isEqualTo(UPDATED_IS_PUBLISHED);
    }

    @Test
    @Transactional
    public void updateNonExistingSignalService() throws Exception {
        int databaseSizeBeforeUpdate = signalServiceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSignalServiceMockMvc.perform(put("/api/signal-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signalService)))
            .andExpect(status().isBadRequest());

        // Validate the SignalService in the database
        List<SignalService> signalServiceList = signalServiceRepository.findAll();
        assertThat(signalServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSignalService() throws Exception {
        // Initialize the database
        signalServiceService.save(signalService);

        int databaseSizeBeforeDelete = signalServiceRepository.findAll().size();

        // Delete the signalService
        restSignalServiceMockMvc.perform(delete("/api/signal-services/{id}", signalService.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SignalService> signalServiceList = signalServiceRepository.findAll();
        assertThat(signalServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
