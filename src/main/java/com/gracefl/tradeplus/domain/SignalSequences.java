package com.gracefl.tradeplus.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * used to store the available signals sequences that lead to entry reasons
 */
@ApiModel(description = "used to store the available signals sequences that lead to entry reasons")
@Entity
@Table(name = "signal_sequences")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SignalSequences implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sequence_name", nullable = false)
    private String sequenceName;

    @NotNull
    @Column(name = "sequence_identifier", nullable = false)
    private String sequenceIdentifier;

    @Lob
    @Column(name = "sequence_description")
    private String sequenceDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public SignalSequences sequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
        return this;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public String getSequenceIdentifier() {
        return sequenceIdentifier;
    }

    public SignalSequences sequenceIdentifier(String sequenceIdentifier) {
        this.sequenceIdentifier = sequenceIdentifier;
        return this;
    }

    public void setSequenceIdentifier(String sequenceIdentifier) {
        this.sequenceIdentifier = sequenceIdentifier;
    }

    public String getSequenceDescription() {
        return sequenceDescription;
    }

    public SignalSequences sequenceDescription(String sequenceDescription) {
        this.sequenceDescription = sequenceDescription;
        return this;
    }

    public void setSequenceDescription(String sequenceDescription) {
        this.sequenceDescription = sequenceDescription;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignalSequences)) {
            return false;
        }
        return id != null && id.equals(((SignalSequences) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SignalSequences{" +
            "id=" + getId() +
            ", sequenceName='" + getSequenceName() + "'" +
            ", sequenceIdentifier='" + getSequenceIdentifier() + "'" +
            ", sequenceDescription='" + getSequenceDescription() + "'" +
            "}";
    }
}
