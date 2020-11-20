package com.gracefl.tradeplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.gracefl.tradeplus.domain.enumeration.TIMEFRAME;

/**
 * A PriceDataHistory.
 */
@Entity
@Table(name = "price_data_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PriceDataHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "price_timeframe", nullable = false)
    private TIMEFRAME priceTimeframe;

    @Column(name = "price_date")
    private LocalDate priceDate;

    @Column(name = "price_time")
    private ZonedDateTime priceTime;

    @Column(name = "price_open")
    private Double priceOpen;

    @Column(name = "price_high")
    private Double priceHigh;

    @Column(name = "price_low")
    private Double priceLow;

    @Column(name = "price_close")
    private Double priceClose;

    @Column(name = "price_volume")
    private Double priceVolume;

    @OneToMany(mappedBy = "priceDataHistory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SignalService> signalServices = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "priceDataHistories", allowSetters = true)
    private Instrument instrument;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TIMEFRAME getPriceTimeframe() {
        return priceTimeframe;
    }

    public PriceDataHistory priceTimeframe(TIMEFRAME priceTimeframe) {
        this.priceTimeframe = priceTimeframe;
        return this;
    }

    public void setPriceTimeframe(TIMEFRAME priceTimeframe) {
        this.priceTimeframe = priceTimeframe;
    }

    public LocalDate getPriceDate() {
        return priceDate;
    }

    public PriceDataHistory priceDate(LocalDate priceDate) {
        this.priceDate = priceDate;
        return this;
    }

    public void setPriceDate(LocalDate priceDate) {
        this.priceDate = priceDate;
    }

    public ZonedDateTime getPriceTime() {
        return priceTime;
    }

    public PriceDataHistory priceTime(ZonedDateTime priceTime) {
        this.priceTime = priceTime;
        return this;
    }

    public void setPriceTime(ZonedDateTime priceTime) {
        this.priceTime = priceTime;
    }

    public Double getPriceOpen() {
        return priceOpen;
    }

    public PriceDataHistory priceOpen(Double priceOpen) {
        this.priceOpen = priceOpen;
        return this;
    }

    public void setPriceOpen(Double priceOpen) {
        this.priceOpen = priceOpen;
    }

    public Double getPriceHigh() {
        return priceHigh;
    }

    public PriceDataHistory priceHigh(Double priceHigh) {
        this.priceHigh = priceHigh;
        return this;
    }

    public void setPriceHigh(Double priceHigh) {
        this.priceHigh = priceHigh;
    }

    public Double getPriceLow() {
        return priceLow;
    }

    public PriceDataHistory priceLow(Double priceLow) {
        this.priceLow = priceLow;
        return this;
    }

    public void setPriceLow(Double priceLow) {
        this.priceLow = priceLow;
    }

    public Double getPriceClose() {
        return priceClose;
    }

    public PriceDataHistory priceClose(Double priceClose) {
        this.priceClose = priceClose;
        return this;
    }

    public void setPriceClose(Double priceClose) {
        this.priceClose = priceClose;
    }

    public Double getPriceVolume() {
        return priceVolume;
    }

    public PriceDataHistory priceVolume(Double priceVolume) {
        this.priceVolume = priceVolume;
        return this;
    }

    public void setPriceVolume(Double priceVolume) {
        this.priceVolume = priceVolume;
    }

    public Set<SignalService> getSignalServices() {
        return signalServices;
    }

    public PriceDataHistory signalServices(Set<SignalService> signalServices) {
        this.signalServices = signalServices;
        return this;
    }

    public PriceDataHistory addSignalService(SignalService signalService) {
        this.signalServices.add(signalService);
        signalService.setPriceDataHistory(this);
        return this;
    }

    public PriceDataHistory removeSignalService(SignalService signalService) {
        this.signalServices.remove(signalService);
        signalService.setPriceDataHistory(null);
        return this;
    }

    public void setSignalServices(Set<SignalService> signalServices) {
        this.signalServices = signalServices;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public PriceDataHistory instrument(Instrument instrument) {
        this.instrument = instrument;
        return this;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PriceDataHistory)) {
            return false;
        }
        return id != null && id.equals(((PriceDataHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PriceDataHistory{" +
            "id=" + getId() +
            ", priceTimeframe='" + getPriceTimeframe() + "'" +
            ", priceDate='" + getPriceDate() + "'" +
            ", priceTime='" + getPriceTime() + "'" +
            ", priceOpen=" + getPriceOpen() +
            ", priceHigh=" + getPriceHigh() +
            ", priceLow=" + getPriceLow() +
            ", priceClose=" + getPriceClose() +
            ", priceVolume=" + getPriceVolume() +
            "}";
    }
}
