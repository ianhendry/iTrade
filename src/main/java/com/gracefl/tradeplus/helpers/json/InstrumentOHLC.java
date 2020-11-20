package com.gracefl.tradeplus.helpers.json;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "instrument", "period", "barTime", "openPrice", "highPrice", "lowPrice", "closePrice",
		"tickVolume", "spread", "realVolume" })
public class InstrumentOHLC {

	@JsonProperty("instrument")
	private String instrument;
	@JsonProperty("period")
	private String period;
	@JsonProperty("barTime")
	private Integer barTime;
	@JsonProperty("openPrice")
	private Double openPrice;
	@JsonProperty("highPrice")
	private Double highPrice;
	@JsonProperty("lowPrice")
	private Double lowPrice;
	@JsonProperty("closePrice")
	private Double closePrice;
	@JsonProperty("tickVolume")
	private Integer tickVolume;
	@JsonProperty("spread")
	private Integer spread;
	@JsonProperty("realVolume")
	private Integer realVolume;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public InstrumentOHLC() {} ;
	
	public InstrumentOHLC(String instrument, String period, Integer barTime, Double openPrice, Double highPrice,
			Double lowPrice, Double closePrice, Integer tickVolume, Integer spread, Integer realVolume) {
		super();
		this.instrument = instrument;
		this.period = period;
		this.barTime = barTime;
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
		this.tickVolume = tickVolume;
		this.spread = spread;
		this.realVolume = realVolume;
	}

	@JsonProperty("instrument")
	public String getInstrument() {
		return instrument;
	}

	@JsonProperty("instrument")
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	@JsonProperty("period")
	public String getPeriod() {
		return period;
	}

	@JsonProperty("period")
	public void setPeriod(String period) {
		this.period = period;
	}

	@JsonProperty("barTime")
	public Integer getBarTime() {
		return barTime;
	}

	@JsonProperty("barTime")
	public void setBarTime(Integer barTime) {
		this.barTime = barTime;
	}

	@JsonProperty("openPrice")
	public Double getOpenPrice() {
		return openPrice;
	}

	@JsonProperty("openPrice")
	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	@JsonProperty("highPrice")
	public Double getHighPrice() {
		return highPrice;
	}

	@JsonProperty("highPrice")
	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}

	@JsonProperty("lowPrice")
	public Double getLowPrice() {
		return lowPrice;
	}

	@JsonProperty("lowPrice")
	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	@JsonProperty("closePrice")
	public Double getClosePrice() {
		return closePrice;
	}

	@JsonProperty("closePrice")
	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}

	@JsonProperty("tickVolume")
	public Integer getTickVolume() {
		return tickVolume;
	}

	@JsonProperty("tickVolume")
	public void setTickVolume(Integer tickVolume) {
		this.tickVolume = tickVolume;
	}

	@JsonProperty("spread")
	public Integer getSpread() {
		return spread;
	}

	@JsonProperty("spread")
	public void setSpread(Integer spread) {
		this.spread = spread;
	}

	@JsonProperty("realVolume")
	public Integer getRealVolume() {
		return realVolume;
	}

	@JsonProperty("realVolume")
	public void setRealVolume(Integer realVolume) {
		this.realVolume = realVolume;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
