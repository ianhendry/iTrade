package com.gracefl.tradeplus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A KeyValuePairs.
 */
@Entity
@Table(name = "key_value_pairs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KeyValuePairs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_value_group")
    private String keyValueGroup;

    @Column(name = "key_value")
    private String keyValue;

    @Column(name = "key_value_entry")
    private String keyValueEntry;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyValueGroup() {
        return keyValueGroup;
    }

    public KeyValuePairs keyValueGroup(String keyValueGroup) {
        this.keyValueGroup = keyValueGroup;
        return this;
    }

    public void setKeyValueGroup(String keyValueGroup) {
        this.keyValueGroup = keyValueGroup;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public KeyValuePairs keyValue(String keyValue) {
        this.keyValue = keyValue;
        return this;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyValueEntry() {
        return keyValueEntry;
    }

    public KeyValuePairs keyValueEntry(String keyValueEntry) {
        this.keyValueEntry = keyValueEntry;
        return this;
    }

    public void setKeyValueEntry(String keyValueEntry) {
        this.keyValueEntry = keyValueEntry;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyValuePairs)) {
            return false;
        }
        return id != null && id.equals(((KeyValuePairs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeyValuePairs{" +
            "id=" + getId() +
            ", keyValueGroup='" + getKeyValueGroup() + "'" +
            ", keyValue='" + getKeyValue() + "'" +
            ", keyValueEntry='" + getKeyValueEntry() + "'" +
            "}";
    }
}
