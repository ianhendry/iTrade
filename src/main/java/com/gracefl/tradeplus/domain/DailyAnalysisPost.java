package com.gracefl.tradeplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.gracefl.tradeplus.domain.enumeration.DAYOFWEEK;

import com.gracefl.tradeplus.domain.enumeration.HIGHVOLBARLOCATION;

/**
 * A DailyAnalysisPost.
 */
@Entity
@Table(name = "daily_analysis_post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DailyAnalysisPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @NotNull
    @Column(name = "date_added", nullable = false)
    private ZonedDateTime dateAdded;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DAYOFWEEK dayOfWeek;

    @NotNull
    @Column(name = "background_volume", nullable = false)
    private String backgroundVolume;

    @NotNull
    @Column(name = "price_action", nullable = false)
    private String priceAction;

    @Column(name = "reasons_to_enter")
    private String reasonsToEnter;

    @Column(name = "warning_signs")
    private String warningSigns;

    @Lob
    @Column(name = "daily_chart_image")
    private byte[] dailyChartImage;

    @Column(name = "daily_chart_image_content_type")
    private String dailyChartImageContentType;

    @Lob
    @Column(name = "one_hr_chart_image")
    private byte[] oneHrChartImage;

    @Column(name = "one_hr_chart_image_content_type")
    private String oneHrChartImageContentType;

    @Lob
    @Column(name = "five_min_chart_image")
    private byte[] fiveMinChartImage;

    @Column(name = "five_min_chart_image_content_type")
    private String fiveMinChartImageContentType;

    @Column(name = "plan_for_today")
    private String planForToday;

    @Column(name = "high_vol_bar")
    private String highVolBar;

    @Enumerated(EnumType.STRING)
    @Column(name = "high_vol_bar_location")
    private HIGHVOLBARLOCATION highVolBarLocation;

    @OneToOne
    @JoinColumn(unique = true)
    private Instrument instrument;

    @ManyToOne
    @JsonIgnoreProperties(value = "dailyAnalysisPosts", allowSetters = true)
    private Mt4Account mt4Account;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public DailyAnalysisPost postTitle(String postTitle) {
        this.postTitle = postTitle;
        return this;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public ZonedDateTime getDateAdded() {
        return dateAdded;
    }

    public DailyAnalysisPost dateAdded(ZonedDateTime dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(ZonedDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public DAYOFWEEK getDayOfWeek() {
        return dayOfWeek;
    }

    public DailyAnalysisPost dayOfWeek(DAYOFWEEK dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(DAYOFWEEK dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getBackgroundVolume() {
        return backgroundVolume;
    }

    public DailyAnalysisPost backgroundVolume(String backgroundVolume) {
        this.backgroundVolume = backgroundVolume;
        return this;
    }

    public void setBackgroundVolume(String backgroundVolume) {
        this.backgroundVolume = backgroundVolume;
    }

    public String getPriceAction() {
        return priceAction;
    }

    public DailyAnalysisPost priceAction(String priceAction) {
        this.priceAction = priceAction;
        return this;
    }

    public void setPriceAction(String priceAction) {
        this.priceAction = priceAction;
    }

    public String getReasonsToEnter() {
        return reasonsToEnter;
    }

    public DailyAnalysisPost reasonsToEnter(String reasonsToEnter) {
        this.reasonsToEnter = reasonsToEnter;
        return this;
    }

    public void setReasonsToEnter(String reasonsToEnter) {
        this.reasonsToEnter = reasonsToEnter;
    }

    public String getWarningSigns() {
        return warningSigns;
    }

    public DailyAnalysisPost warningSigns(String warningSigns) {
        this.warningSigns = warningSigns;
        return this;
    }

    public void setWarningSigns(String warningSigns) {
        this.warningSigns = warningSigns;
    }

    public byte[] getDailyChartImage() {
        return dailyChartImage;
    }

    public DailyAnalysisPost dailyChartImage(byte[] dailyChartImage) {
        this.dailyChartImage = dailyChartImage;
        return this;
    }

    public void setDailyChartImage(byte[] dailyChartImage) {
        this.dailyChartImage = dailyChartImage;
    }

    public String getDailyChartImageContentType() {
        return dailyChartImageContentType;
    }

    public DailyAnalysisPost dailyChartImageContentType(String dailyChartImageContentType) {
        this.dailyChartImageContentType = dailyChartImageContentType;
        return this;
    }

    public void setDailyChartImageContentType(String dailyChartImageContentType) {
        this.dailyChartImageContentType = dailyChartImageContentType;
    }

    public byte[] getOneHrChartImage() {
        return oneHrChartImage;
    }

    public DailyAnalysisPost oneHrChartImage(byte[] oneHrChartImage) {
        this.oneHrChartImage = oneHrChartImage;
        return this;
    }

    public void setOneHrChartImage(byte[] oneHrChartImage) {
        this.oneHrChartImage = oneHrChartImage;
    }

    public String getOneHrChartImageContentType() {
        return oneHrChartImageContentType;
    }

    public DailyAnalysisPost oneHrChartImageContentType(String oneHrChartImageContentType) {
        this.oneHrChartImageContentType = oneHrChartImageContentType;
        return this;
    }

    public void setOneHrChartImageContentType(String oneHrChartImageContentType) {
        this.oneHrChartImageContentType = oneHrChartImageContentType;
    }

    public byte[] getFiveMinChartImage() {
        return fiveMinChartImage;
    }

    public DailyAnalysisPost fiveMinChartImage(byte[] fiveMinChartImage) {
        this.fiveMinChartImage = fiveMinChartImage;
        return this;
    }

    public void setFiveMinChartImage(byte[] fiveMinChartImage) {
        this.fiveMinChartImage = fiveMinChartImage;
    }

    public String getFiveMinChartImageContentType() {
        return fiveMinChartImageContentType;
    }

    public DailyAnalysisPost fiveMinChartImageContentType(String fiveMinChartImageContentType) {
        this.fiveMinChartImageContentType = fiveMinChartImageContentType;
        return this;
    }

    public void setFiveMinChartImageContentType(String fiveMinChartImageContentType) {
        this.fiveMinChartImageContentType = fiveMinChartImageContentType;
    }

    public String getPlanForToday() {
        return planForToday;
    }

    public DailyAnalysisPost planForToday(String planForToday) {
        this.planForToday = planForToday;
        return this;
    }

    public void setPlanForToday(String planForToday) {
        this.planForToday = planForToday;
    }

    public String getHighVolBar() {
        return highVolBar;
    }

    public DailyAnalysisPost highVolBar(String highVolBar) {
        this.highVolBar = highVolBar;
        return this;
    }

    public void setHighVolBar(String highVolBar) {
        this.highVolBar = highVolBar;
    }

    public HIGHVOLBARLOCATION getHighVolBarLocation() {
        return highVolBarLocation;
    }

    public DailyAnalysisPost highVolBarLocation(HIGHVOLBARLOCATION highVolBarLocation) {
        this.highVolBarLocation = highVolBarLocation;
        return this;
    }

    public void setHighVolBarLocation(HIGHVOLBARLOCATION highVolBarLocation) {
        this.highVolBarLocation = highVolBarLocation;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public DailyAnalysisPost instrument(Instrument instrument) {
        this.instrument = instrument;
        return this;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public Mt4Account getMt4Account() {
        return mt4Account;
    }

    public DailyAnalysisPost mt4Account(Mt4Account mt4Account) {
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
        if (!(o instanceof DailyAnalysisPost)) {
            return false;
        }
        return id != null && id.equals(((DailyAnalysisPost) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyAnalysisPost{" +
            "id=" + getId() +
            ", postTitle='" + getPostTitle() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", backgroundVolume='" + getBackgroundVolume() + "'" +
            ", priceAction='" + getPriceAction() + "'" +
            ", reasonsToEnter='" + getReasonsToEnter() + "'" +
            ", warningSigns='" + getWarningSigns() + "'" +
            ", dailyChartImage='" + getDailyChartImage() + "'" +
            ", dailyChartImageContentType='" + getDailyChartImageContentType() + "'" +
            ", oneHrChartImage='" + getOneHrChartImage() + "'" +
            ", oneHrChartImageContentType='" + getOneHrChartImageContentType() + "'" +
            ", fiveMinChartImage='" + getFiveMinChartImage() + "'" +
            ", fiveMinChartImageContentType='" + getFiveMinChartImageContentType() + "'" +
            ", planForToday='" + getPlanForToday() + "'" +
            ", highVolBar='" + getHighVolBar() + "'" +
            ", highVolBarLocation='" + getHighVolBarLocation() + "'" +
            "}";
    }
}
