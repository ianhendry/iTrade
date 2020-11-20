package com.gracefl.tradeplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.gracefl.tradeplus.domain.enumeration.SIGNALINDICATES;

/**
 * used to store the available signals - strength indications & weakness indications
 */
@ApiModel(description = "used to store the available signals - strength indications & weakness indications")
@Entity
@Table(name = "trade_signals")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TradeSignals implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "number_of_bars")
    private Integer numberOfBars;

    @Enumerated(EnumType.STRING)
    @Column(name = "signal_indicates")
    private SIGNALINDICATES signalIndicates;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "background")
    private String background;

    @Lob
    @Column(name = "action_to_take")
    private String actionToTake;

    @Column(name = "sequence_number")
    private Integer sequenceNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = "tradeSignals", allowSetters = true)
    private SignalSequences signalsSequences;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TradeSignals name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public TradeSignals note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getNumberOfBars() {
        return numberOfBars;
    }

    public TradeSignals numberOfBars(Integer numberOfBars) {
        this.numberOfBars = numberOfBars;
        return this;
    }

    public void setNumberOfBars(Integer numberOfBars) {
        this.numberOfBars = numberOfBars;
    }

    public SIGNALINDICATES getSignalIndicates() {
        return signalIndicates;
    }

    public TradeSignals signalIndicates(SIGNALINDICATES signalIndicates) {
        this.signalIndicates = signalIndicates;
        return this;
    }

    public void setSignalIndicates(SIGNALINDICATES signalIndicates) {
        this.signalIndicates = signalIndicates;
    }

    public String getDescription() {
        return description;
    }

    public TradeSignals description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public TradeSignals background(String background) {
        this.background = background;
        return this;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getActionToTake() {
        return actionToTake;
    }

    public TradeSignals actionToTake(String actionToTake) {
        this.actionToTake = actionToTake;
        return this;
    }

    public void setActionToTake(String actionToTake) {
        this.actionToTake = actionToTake;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public TradeSignals sequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public SignalSequences getSignalsSequences() {
        return signalsSequences;
    }

    public TradeSignals signalsSequences(SignalSequences signalSequences) {
        this.signalsSequences = signalSequences;
        return this;
    }

    public void setSignalsSequences(SignalSequences signalSequences) {
        this.signalsSequences = signalSequences;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TradeSignals)) {
            return false;
        }
        return id != null && id.equals(((TradeSignals) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TradeSignals{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", note='" + getNote() + "'" +
            ", numberOfBars=" + getNumberOfBars() +
            ", signalIndicates='" + getSignalIndicates() + "'" +
            ", description='" + getDescription() + "'" +
            ", background='" + getBackground() + "'" +
            ", actionToTake='" + getActionToTake() + "'" +
            ", sequenceNumber=" + getSequenceNumber() +
            "}";
    }
}
