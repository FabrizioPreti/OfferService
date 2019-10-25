package it.worldpay.faz.offerservice.dto;

import java.math.BigDecimal;

public class CurrencyOfferDTO {
	
	private String currencyId;
	private String currencyDescription;
	private BigDecimal currencyRate;
	
	public CurrencyOfferDTO() {}
	
	public CurrencyOfferDTO(String currencyId) {
		this.currencyId = currencyId;
	}
	
	public CurrencyOfferDTO(String currencyId, String currencyDescription, 
							BigDecimal currencyRate) {//, List<OfferDTO> offerList) {
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
		return "CurrencyOfferDTO [currencyId=" + currencyId + ", currencyDescription=" + currencyDescription
				+ ", currencyRate=" + currencyRate + "]";
	}


}
