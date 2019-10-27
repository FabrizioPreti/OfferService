package it.worldpay.faz.offerservice.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author FPreti
 *
 */
@Entity(name="CURRENCY")
public class CurrencyOffer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5114942949606184164L;
	
	@Id
	@Column(name = "CURRENCY_ID")
	private String currencyId;
	
	@Column(name = "CURRENCY_DESCR")
	private String currencyDescription;
	
	@Column(name = "CURRENCY_RATE")
	private BigDecimal currencyRate;
	
	public CurrencyOffer() {}
	
	public CurrencyOffer(String currencyId) {
		this.currencyId = currencyId;
	}

	public CurrencyOffer(String currencyId, String currencyDescription, 
							BigDecimal currencyRate) {
		super();
		this.currencyId = currencyId;
		this.currencyDescription = currencyDescription;
		this.currencyRate = currencyRate;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyDescription() {
		return currencyDescription;
	}

	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
	}

	public BigDecimal getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}

	@Override
	public String toString() {
		return "CurrencyOffer [currencyId=" + currencyId + ", currencyDescription=" + currencyDescription
				+ ", currencyRate=" + currencyRate + "]";
	}
	
}
