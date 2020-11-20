package com.gracefl.tradeplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A SiteAccount.
 */
@Entity
@Table(name = "site_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SiteAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_email")
    private String accountEmail;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_real")
    private Boolean accountReal;

    @Column(name = "account_balance", precision = 21, scale = 2)
    private BigDecimal accountBalance;

    @Column(name = "account_open_date")
    private LocalDate accountOpenDate;

    @Column(name = "account_close_date")
    private LocalDate accountCloseDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "siteAccounts", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "siteAccounts", allowSetters = true)
    private Mt4Account mt4Account;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public SiteAccount accountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
        return this;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getAccountName() {
        return accountName;
    }

    public SiteAccount accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Boolean isAccountReal() {
        return accountReal;
    }

    public SiteAccount accountReal(Boolean accountReal) {
        this.accountReal = accountReal;
        return this;
    }

    public void setAccountReal(Boolean accountReal) {
        this.accountReal = accountReal;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public SiteAccount accountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
        return this;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public LocalDate getAccountOpenDate() {
        return accountOpenDate;
    }

    public SiteAccount accountOpenDate(LocalDate accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
        return this;
    }

    public void setAccountOpenDate(LocalDate accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
    }

    public LocalDate getAccountCloseDate() {
        return accountCloseDate;
    }

    public SiteAccount accountCloseDate(LocalDate accountCloseDate) {
        this.accountCloseDate = accountCloseDate;
        return this;
    }

    public void setAccountCloseDate(LocalDate accountCloseDate) {
        this.accountCloseDate = accountCloseDate;
    }

    public User getUser() {
        return user;
    }

    public SiteAccount user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Mt4Account getMt4Account() {
        return mt4Account;
    }

    public SiteAccount mt4Account(Mt4Account mt4Account) {
        this.mt4Account = mt4Account;
        return this;
    }

    public void setMt4Account(Mt4Account mt4Account) {
        this.mt4Account = mt4Account;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteAccount)) {
            return false;
        }
        return id != null && id.equals(((SiteAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteAccount{" +
            "id=" + getId() +
            ", accountEmail='" + getAccountEmail() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", accountReal='" + isAccountReal() + "'" +
            ", accountBalance=" + getAccountBalance() +
            ", accountOpenDate='" + getAccountOpenDate() + "'" +
            ", accountCloseDate='" + getAccountCloseDate() + "'" +
            "}";
    }
}
