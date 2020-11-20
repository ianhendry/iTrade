package com.gracefl.tradeplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.gracefl.tradeplus.domain.enumeration.DATAPROVIDER;

/**
 * A Instrument.
 */
@Entity
@Table(name = "instrument")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Instrument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_provider")
    private DATAPROVIDER dataProvider;

    @NotNull
    @Column(name = "ticker", nullable = false)
    private String ticker;

    @Column(name = "exchange")
    private String exchange;

    @Column(name = "description")
    private String description;

    @Column(name = "data_from")
    private LocalDate dataFrom;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "date_inactive")
    private LocalDate dateInactive;

    @ManyToMany(mappedBy = "intruments")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Watchlist> watchlists = new HashSet<>();

    @ManyToMany(mappedBy = "intruments")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<SignalService> signalServices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DATAPROVIDER getDataProvider() {
        return dataProvider;
    }

    public Instrument dataProvider(DATAPROVIDER dataProvider) {
        this.dataProvider = dataProvider;
        return this;
    }

    public void setDataProvider(DATAPROVIDER dataProvider) {
        this.dataProvider = dataProvider;
    }

    public String getTicker() {
        return ticker;
    }

    public Instrument ticker(String ticker) {
        this.ticker = ticker;
        return this;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getExchange() {
        return exchange;
    }

    public Instrument exchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getDescription() {
        return description;
    }

    public Instrument description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDataFrom() {
        return dataFrom;
    }

    public Instrument dataFrom(LocalDate dataFrom) {
        this.dataFrom = dataFrom;
        return this;
    }

    public void setDataFrom(LocalDate dataFrom) {
        this.dataFrom = dataFrom;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Instrument isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public Instrument dateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateInactive() {
        return dateInactive;
    }

    public Instrument dateInactive(LocalDate dateInactive) {
        this.dateInactive = dateInactive;
        return this;
    }

    public void setDateInactive(LocalDate dateInactive) {
        this.dateInactive = dateInactive;
    }

    public Set<Watchlist> getWatchlists() {
        return watchlists;
    }

    public Instrument watchlists(Set<Watchlist> watchlists) {
        this.watchlists = watchlists;
        return this;
    }

    public Instrument addWatchlist(Watchlist watchlist) {
        this.watchlists.add(watchlist);
        watchlist.getIntruments().add(this);
        return this;
    }

    public Instrument removeWatchlist(Watchlist watchlist) {
        this.watchlists.remove(watchlist);
        watchlist.getIntruments().remove(this);
        return this;
    }

    public void setWatchlists(Set<Watchlist> watchlists) {
        this.watchlists = watchlists;
    }

    public Set<SignalService> getSignalServices() {
        return signalServices;
    }

    public Instrument signalServices(Set<SignalService> signalServices) {
        this.signalServices = signalServices;
        return this;
    }

    public Instrument addSignalService(SignalService signalService) {
        this.signalServices.add(signalService);
        signalService.getIntruments().add(this);
        return this;
    }

    public Instrument removeSignalService(SignalService signalService) {
        this.signalServices.remove(signalService);
        signalService.getIntruments().remove(this);
        return this;
    }

    public void setSignalServices(Set<SignalService> signalServices) {
        this.signalServices = signalServices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Instrument)) {
            return false;
        }
        return id != null && id.equals(((Instrument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Instrument{" +
            "id=" + getId() +
            ", dataProvider='" + getDataProvider() + "'" +
            ", ticker='" + getTicker() + "'" +
            ", exchange='" + getExchange() + "'" +
            ", description='" + getDescription() + "'" +
            ", dataFrom='" + getDataFrom() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dateInactive='" + getDateInactive() + "'" +
            "}";
    }
}
