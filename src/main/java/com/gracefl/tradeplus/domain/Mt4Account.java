package com.gracefl.tradeplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.gracefl.tradeplus.domain.enumeration.ACCOUNTTYPE;

import com.gracefl.tradeplus.domain.enumeration.BROKER;

/**
 * A Mt4Account.
 */
@Entity
@Table(name = "mt_4_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mt4Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private ACCOUNTTYPE accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_broker")
    private BROKER accountBroker;

    @Column(name = "account_login")
    private String accountLogin;

    @Column(name = "account_password")
    private String accountPassword;

    @Column(name = "account_active")
    private Boolean accountActive;

    @Column(name = "account_close_date")
    private LocalDate accountCloseDate;

    @OneToMany(mappedBy = "mt4Account")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<DailyAnalysisPost> dailyAnalysisPosts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "mt4Accounts", allowSetters = true)
    private Mt4Trade mt4Trade;

    @ManyToOne
    @JsonIgnoreProperties(value = "mt4Accounts", allowSetters = true)
    private Watchlist watchlist;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ACCOUNTTYPE getAccountType() {
        return accountType;
    }

    public Mt4Account accountType(ACCOUNTTYPE accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(ACCOUNTTYPE accountType) {
        this.accountType = accountType;
    }

    public BROKER getAccountBroker() {
        return accountBroker;
    }

    public Mt4Account accountBroker(BROKER accountBroker) {
        this.accountBroker = accountBroker;
        return this;
    }

    public void setAccountBroker(BROKER accountBroker) {
        this.accountBroker = accountBroker;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public Mt4Account accountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
        return this;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public Mt4Account accountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
        return this;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public Boolean isAccountActive() {
        return accountActive;
    }

    public Mt4Account accountActive(Boolean accountActive) {
        this.accountActive = accountActive;
        return this;
    }

    public void setAccountActive(Boolean accountActive) {
        this.accountActive = accountActive;
    }

    public LocalDate getAccountCloseDate() {
        return accountCloseDate;
    }

    public Mt4Account accountCloseDate(LocalDate accountCloseDate) {
        this.accountCloseDate = accountCloseDate;
        return this;
    }

    public void setAccountCloseDate(LocalDate accountCloseDate) {
        this.accountCloseDate = accountCloseDate;
    }

    public Set<DailyAnalysisPost> getDailyAnalysisPosts() {
        return dailyAnalysisPosts;
    }

    public Mt4Account dailyAnalysisPosts(Set<DailyAnalysisPost> dailyAnalysisPosts) {
        this.dailyAnalysisPosts = dailyAnalysisPosts;
        return this;
    }

    public Mt4Account addDailyAnalysisPost(DailyAnalysisPost dailyAnalysisPost) {
        this.dailyAnalysisPosts.add(dailyAnalysisPost);
        dailyAnalysisPost.setMt4Account(this);
        return this;
    }

    public Mt4Account removeDailyAnalysisPost(DailyAnalysisPost dailyAnalysisPost) {
        this.dailyAnalysisPosts.remove(dailyAnalysisPost);
        dailyAnalysisPost.setMt4Account(null);
        return this;
    }

    public void setDailyAnalysisPosts(Set<DailyAnalysisPost> dailyAnalysisPosts) {
        this.dailyAnalysisPosts = dailyAnalysisPosts;
    }

    public Mt4Trade getMt4Trade() {
        return mt4Trade;
    }

    public Mt4Account mt4Trade(Mt4Trade mt4Trade) {
        this.mt4Trade = mt4Trade;
        return this;
    }

    public void setMt4Trade(Mt4Trade mt4Trade) {
        this.mt4Trade = mt4Trade;
    }

    public Watchlist getWatchlist() {
        return watchlist;
    }

    public Mt4Account watchlist(Watchlist watchlist) {
        this.watchlist = watchlist;
        return this;
    }

    public void setWatchlist(Watchlist watchlist) {
        this.watchlist = watchlist;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mt4Account)) {
            return false;
        }
        return id != null && id.equals(((Mt4Account) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mt4Account{" +
            "id=" + getId() +
            ", accountType='" + getAccountType() + "'" +
            ", accountBroker='" + getAccountBroker() + "'" +
            ", accountLogin='" + getAccountLogin() + "'" +
            ", accountPassword='" + getAccountPassword() + "'" +
            ", accountActive='" + isAccountActive() + "'" +
            ", accountCloseDate='" + getAccountCloseDate() + "'" +
            "}";
    }
}
