package com.gracefl.tradeplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.gracefl.tradeplus.domain.enumeration.SIGNALINDICATES;

import com.gracefl.tradeplus.domain.enumeration.TIMEFRAME;

import com.gracefl.tradeplus.domain.enumeration.SIGNALBARSIZE;

import com.gracefl.tradeplus.domain.enumeration.BARCLOSE;

/**
 * used to persist when a signal is found
 */
@ApiModel(description = "used to persist when a signal is found")
@Entity
@Table(name = "signal_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SignalService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "alert_date", nullable = false)
    private LocalDate alertDate;

    @Column(name = "alert_time")
    private ZonedDateTime alertTime;

    @Column(name = "alert_text")
    private String alertText;

    @Lob
    @Column(name = "alert_description")
    private String alertDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "signal_indicates")
    private SIGNALINDICATES signalIndicates;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "timeframe", nullable = false)
    private TIMEFRAME timeframe;

    @Column(name = "alert_price")
    private Double alertPrice;

    @Column(name = "is_sequence_present")
    private Boolean isSequencePresent;

    @Column(name = "bar_volume", precision = 21, scale = 2)
    private BigDecimal barVolume;

    @Enumerated(EnumType.STRING)
    @Column(name = "bar_size")
    private SIGNALBARSIZE barSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "bar_close")
    private BARCLOSE barClose;

    @Column(name = "is_published")
    private Boolean isPublished;

    @ManyToOne
    @JsonIgnoreProperties(value = "signalServices", allowSetters = true)
    private TradeSignals tradeSignals;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "signal_service_intrument",
               joinColumns = @JoinColumn(name = "signal_service_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "intrument_id", referencedColumnName = "id"))
    private Set<Instrument> intruments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "signalServices", allowSetters = true)
    private PriceDataHistory priceDataHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAlertDate() {
        return alertDate;
    }

    public SignalService alertDate(LocalDate alertDate) {
        this.alertDate = alertDate;
        return this;
    }

    public void setAlertDate(LocalDate alertDate) {
        this.alertDate = alertDate;
    }

    public ZonedDateTime getAlertTime() {
        return alertTime;
    }

    public SignalService alertTime(ZonedDateTime alertTime) {
        this.alertTime = alertTime;
        return this;
    }

    public void setAlertTime(ZonedDateTime alertTime) {
        this.alertTime = alertTime;
    }

    public String getAlertText() {
        return alertText;
    }

    public SignalService alertText(String alertText) {
        this.alertText = alertText;
        return this;
    }

    public void setAlertText(String alertText) {
        this.alertText = alertText;
    }

    public String getAlertDescription() {
        return alertDescription;
    }

    public SignalService alertDescription(String alertDescription) {
        this.alertDescription = alertDescription;
        return this;
    }

    public void setAlertDescription(String alertDescription) {
        this.alertDescription = alertDescription;
    }

    public SIGNALINDICATES getSignalIndicates() {
        return signalIndicates;
    }

    public SignalService signalIndicates(SIGNALINDICATES signalIndicates) {
        this.signalIndicates = signalIndicates;
        return this;
    }

    public void setSignalIndicates(SIGNALINDICATES signalIndicates) {
        this.signalIndicates = signalIndicates;
    }

    public byte[] getImage() {
        return image;
    }

    public SignalService image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public SignalService imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public TIMEFRAME getTimeframe() {
        return timeframe;
    }

    public SignalService timeframe(TIMEFRAME timeframe) {
        this.timeframe = timeframe;
        return this;
    }

    public void setTimeframe(TIMEFRAME timeframe) {
        this.timeframe = timeframe;
    }

    public Double getAlertPrice() {
        return alertPrice;
    }

    public SignalService alertPrice(Double alertPrice) {
        this.alertPrice = alertPrice;
        return this;
    }

    public void setAlertPrice(Double alertPrice) {
        this.alertPrice = alertPrice;
    }

    public Boolean isIsSequencePresent() {
        return isSequencePresent;
    }

    public SignalService isSequencePresent(Boolean isSequencePresent) {
        this.isSequencePresent = isSequencePresent;
        return this;
    }

    public void setIsSequencePresent(Boolean isSequencePresent) {
        this.isSequencePresent = isSequencePresent;
    }

    public BigDecimal getBarVolume() {
        return barVolume;
    }

    public SignalService barVolume(BigDecimal barVolume) {
        this.barVolume = barVolume;
        return this;
    }

    public void setBarVolume(BigDecimal barVolume) {
        this.barVolume = barVolume;
    }

    public SIGNALBARSIZE getBarSize() {
        return barSize;
    }

    public SignalService barSize(SIGNALBARSIZE barSize) {
        this.barSize = barSize;
        return this;
    }

    public void setBarSize(SIGNALBARSIZE barSize) {
        this.barSize = barSize;
    }

    public BARCLOSE getBarClose() {
        return barClose;
    }

    public SignalService barClose(BARCLOSE barClose) {
        this.barClose = barClose;
        return this;
    }

    public void setBarClose(BARCLOSE barClose) {
        this.barClose = barClose;
    }

    public Boolean isIsPublished() {
        return isPublished;
    }

    public SignalService isPublished(Boolean isPublished) {
        this.isPublished = isPublished;
        return this;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public TradeSignals getTradeSignals() {
        return tradeSignals;
    }

    public SignalService tradeSignals(TradeSignals tradeSignals) {
        this.tradeSignals = tradeSignals;
        return this;
    }

    public void setTradeSignals(TradeSignals tradeSignals) {
        this.tradeSignals = tradeSignals;
    }

    public Set<Instrument> getIntruments() {
        return intruments;
    }

    public SignalService intruments(Set<Instrument> instruments) {
        this.intruments = instruments;
        return this;
    }

    public SignalService addIntrument(Instrument instrument) {
        this.intruments.add(instrument);
        instrument.getSignalServices().add(this);
        return this;
    }

    public SignalService removeIntrument(Instrument instrument) {
        this.intruments.remove(instrument);
        instrument.getSignalServices().remove(this);
        return this;
    }

    public void setIntruments(Set<Instrument> instruments) {
        this.intruments = instruments;
    }

    public PriceDataHistory getPriceDataHistory() {
        return priceDataHistory;
    }

    public SignalService priceDataHistory(PriceDataHistory priceDataHistory) {
        this.priceDataHistory = priceDataHistory;
        return this;
    }

    public void setPriceDataHistory(PriceDataHistory priceDataHistory) {
        this.priceDataHistory = priceDataHistory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignalService)) {
            return false;
        }
        return id != null && id.equals(((SignalService) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SignalService{" +
            "id=" + getId() +
            ", alertDate='" + getAlertDate() + "'" +
            ", alertTime='" + getAlertTime() + "'" +
            ", alertText='" + getAlertText() + "'" +
            ", alertDescription='" + getAlertDescription() + "'" +
            ", signalIndicates='" + getSignalIndicates() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", timeframe='" + getTimeframe() + "'" +
            ", alertPrice=" + getAlertPrice() +
            ", isSequencePresent='" + isIsSequencePresent() + "'" +
            ", barVolume=" + getBarVolume() +
            ", barSize='" + getBarSize() + "'" +
            ", barClose='" + getBarClose() + "'" +
            ", isPublished='" + isIsPublished() + "'" +
            "}";
    }
}
