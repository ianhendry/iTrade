package com.gracefl.tradeplus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * A Mt4Trade.
 */
@Entity
@Table(name = "mt_4_trade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mt4Trade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ticket", precision = 21, scale = 2, nullable = false)
    private BigDecimal ticket;

    @Column(name = "open_time")
    private ZonedDateTime openTime;

    @Column(name = "direction_type")
    private String directionType;

    @Column(name = "position_size")
    private Double positionSize;

    @Column(name = "open_price")
    private Double openPrice;

    @Column(name = "stop_loss_price")
    private Double stopLossPrice;

    @Column(name = "take_profit_price")
    private Double takeProfitPrice;

    @Column(name = "close_time")
    private ZonedDateTime closeTime;

    @Column(name = "close_price")
    private Double closePrice;

    @Column(name = "commission")
    private Double commission;

    @Column(name = "taxes")
    private Double taxes;

    @Column(name = "swap")
    private Double swap;

    @Column(name = "profit")
    private Double profit;

    @OneToOne
    @JoinColumn(unique = true)
    private TradeJournalPost tradeJournalPost;

    @OneToOne
    @JoinColumn(unique = true)
    private Instrument instrument;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTicket() {
        return ticket;
    }

    public Mt4Trade ticket(BigDecimal ticket) {
        this.ticket = ticket;
        return this;
    }

    public void setTicket(BigDecimal ticket) {
        this.ticket = ticket;
    }

    public ZonedDateTime getOpenTime() {
        return openTime;
    }

    public Mt4Trade openTime(ZonedDateTime openTime) {
        this.openTime = openTime;
        return this;
    }

    public void setOpenTime(ZonedDateTime openTime) {
        this.openTime = openTime;
    }

    public String getDirectionType() {
        return directionType;
    }

    public Mt4Trade directionType(String directionType) {
        this.directionType = directionType;
        return this;
    }

    public void setDirectionType(String directionType) {
        this.directionType = directionType;
    }

    public Double getPositionSize() {
        return positionSize;
    }

    public Mt4Trade positionSize(Double positionSize) {
        this.positionSize = positionSize;
        return this;
    }

    public void setPositionSize(Double positionSize) {
        this.positionSize = positionSize;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public Mt4Trade openPrice(Double openPrice) {
        this.openPrice = openPrice;
        return this;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getStopLossPrice() {
        return stopLossPrice;
    }

    public Mt4Trade stopLossPrice(Double stopLossPrice) {
        this.stopLossPrice = stopLossPrice;
        return this;
    }

    public void setStopLossPrice(Double stopLossPrice) {
        this.stopLossPrice = stopLossPrice;
    }

    public Double getTakeProfitPrice() {
        return takeProfitPrice;
    }

    public Mt4Trade takeProfitPrice(Double takeProfitPrice) {
        this.takeProfitPrice = takeProfitPrice;
        return this;
    }

    public void setTakeProfitPrice(Double takeProfitPrice) {
        this.takeProfitPrice = takeProfitPrice;
    }

    public ZonedDateTime getCloseTime() {
        return closeTime;
    }

    public Mt4Trade closeTime(ZonedDateTime closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public void setCloseTime(ZonedDateTime closeTime) {
        this.closeTime = closeTime;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public Mt4Trade closePrice(Double closePrice) {
        this.closePrice = closePrice;
        return this;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getCommission() {
        return commission;
    }

    public Mt4Trade commission(Double commission) {
        this.commission = commission;
        return this;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getTaxes() {
        return taxes;
    }

    public Mt4Trade taxes(Double taxes) {
        this.taxes = taxes;
        return this;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public Double getSwap() {
        return swap;
    }

    public Mt4Trade swap(Double swap) {
        this.swap = swap;
        return this;
    }

    public void setSwap(Double swap) {
        this.swap = swap;
    }

    public Double getProfit() {
        return profit;
    }

    public Mt4Trade profit(Double profit) {
        this.profit = profit;
        return this;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public TradeJournalPost getTradeJournalPost() {
        return tradeJournalPost;
    }

    public Mt4Trade tradeJournalPost(TradeJournalPost tradeJournalPost) {
        this.tradeJournalPost = tradeJournalPost;
        return this;
    }

    public void setTradeJournalPost(TradeJournalPost tradeJournalPost) {
        this.tradeJournalPost = tradeJournalPost;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Mt4Trade instrument(Instrument instrument) {
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
        if (!(o instanceof Mt4Trade)) {
            return false;
        }
        return id != null && id.equals(((Mt4Trade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mt4Trade{" +
            "id=" + getId() +
            ", ticket=" + getTicket() +
            ", openTime='" + getOpenTime() + "'" +
            ", directionType='" + getDirectionType() + "'" +
            ", positionSize=" + getPositionSize() +
            ", openPrice=" + getOpenPrice() +
            ", stopLossPrice=" + getStopLossPrice() +
            ", takeProfitPrice=" + getTakeProfitPrice() +
            ", closeTime='" + getCloseTime() + "'" +
            ", closePrice=" + getClosePrice() +
            ", commission=" + getCommission() +
            ", taxes=" + getTaxes() +
            ", swap=" + getSwap() +
            ", profit=" + getProfit() +
            "}";
    }
}
