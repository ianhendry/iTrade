package com.gracefl.tradeplus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Watchlist.
 */
@Entity
@Table(name = "watchlist")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Watchlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "watchlist_name", nullable = false)
    private String watchlistName;

    @Column(name = "watchlist_description")
    private String watchlistDescription;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Column(name = "date_inactive")
    private LocalDate dateInactive;

    @Column(name = "watchlist_inactive")
    private Boolean watchlistInactive;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "watchlist_intrument",
               joinColumns = @JoinColumn(name = "watchlist_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "intrument_id", referencedColumnName = "id"))
    private Set<Instrument> intruments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWatchlistName() {
        return watchlistName;
    }

    public Watchlist watchlistName(String watchlistName) {
        this.watchlistName = watchlistName;
        return this;
    }

    public void setWatchlistName(String watchlistName) {
        this.watchlistName = watchlistName;
    }

    public String getWatchlistDescription() {
        return watchlistDescription;
    }

    public Watchlist watchlistDescription(String watchlistDescription) {
        this.watchlistDescription = watchlistDescription;
        return this;
    }

    public void setWatchlistDescription(String watchlistDescription) {
        this.watchlistDescription = watchlistDescription;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Watchlist dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateInactive() {
        return dateInactive;
    }

    public Watchlist dateInactive(LocalDate dateInactive) {
        this.dateInactive = dateInactive;
        return this;
    }

    public void setDateInactive(LocalDate dateInactive) {
        this.dateInactive = dateInactive;
    }

    public Boolean isWatchlistInactive() {
        return watchlistInactive;
    }

    public Watchlist watchlistInactive(Boolean watchlistInactive) {
        this.watchlistInactive = watchlistInactive;
        return this;
    }

    public void setWatchlistInactive(Boolean watchlistInactive) {
        this.watchlistInactive = watchlistInactive;
    }

    public Set<Instrument> getIntruments() {
        return intruments;
    }

    public Watchlist intruments(Set<Instrument> instruments) {
        this.intruments = instruments;
        return this;
    }

    public Watchlist addIntrument(Instrument instrument) {
        this.intruments.add(instrument);
        instrument.getWatchlists().add(this);
        return this;
    }

    public Watchlist removeIntrument(Instrument instrument) {
        this.intruments.remove(instrument);
        instrument.getWatchlists().remove(this);
        return this;
    }

    public void setIntruments(Set<Instrument> instruments) {
        this.intruments = instruments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Watchlist)) {
            return false;
        }
        return id != null && id.equals(((Watchlist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Watchlist{" +
            "id=" + getId() +
            ", watchlistName='" + getWatchlistName() + "'" +
            ", watchlistDescription='" + getWatchlistDescription() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateInactive='" + getDateInactive() + "'" +
            ", watchlistInactive='" + isWatchlistInactive() + "'" +
            "}";
    }
}
