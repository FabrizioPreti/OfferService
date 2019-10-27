package it.worldpay.faz.offerservice.dto;

import java.util.Date;
import java.util.List;

/**
 * @author FPreti
 *
 */
public class OfferDTO {
	
	private String offerId;
	private Date offerExpiringDate;
	private Date offerStartingDate;
	private Integer offerDiscountPercent;
	private String offerDescription;
	private boolean isExpired;
	private List<ProductDTO> productList;
	private CurrencyOfferDTO currency;
	private String offerCurrencyDescription;
	
	public OfferDTO(){}
	
	public OfferDTO(String offerId) {
		this.offerId = offerId;
	}
	
	public OfferDTO(String offerId, Date offerExpiringDate, Date offerStartingDate, 
			Integer offerDiscountPercent, String offerDescription, boolean isExpired, 
			List<ProductDTO> productList, CurrencyOfferDTO currency, String offerCurrencyDescription) {
		super();
		this.offerId = offerId;
		this.offerExpiringDate = offerExpiringDate;
		this.offerStartingDate = offerStartingDate;
		this.offerDiscountPercent = offerDiscountPercent;
		this.offerDescription = offerDescription;
		this.isExpired = isExpired;
		this.productList = productList;
		this.currency = currency;
		this.offerCurrencyDescription = offerCurrencyDescription;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public Date getOfferExpiringDate() {
		return offerExpiringDate;
	}

	public void setOfferExpiringDate(Date offerExpiringDate) {
		this.offerExpiringDate = offerExpiringDate;
	}

	public Date getOfferStartingDate() {
		return offerStartingDate;
	}

	public void setOfferStartingDate(Date offerStartingDate) {
		this.offerStartingDate = offerStartingDate;
	}

	public Integer getOfferDiscountPercent() {
		return offerDiscountPercent;
	}

	public void setOfferDiscountPercent(Integer offerDiscountPercent) {
		this.offerDiscountPercent = offerDiscountPercent;
	}

	public String getOfferDescription() {
		return offerDescription;
	}

	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public List<ProductDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDTO> productList) {
		this.productList = productList;
	}

	public CurrencyOfferDTO getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyOfferDTO currency) {
		this.currency = currency;
	}

	public String getOfferCurrencyDescription() {
		return offerCurrencyDescription;
	}

	public void setOfferCurrencyDescription(String offerCurrencyDescription) {
		this.offerCurrencyDescription = offerCurrencyDescription;
	}

	@Override
	public String toString() {
		return "OfferDTO [offerId=" + offerId + ", offerExpiringDate=" + offerExpiringDate + ", offerStartingDate="
				+ offerStartingDate + ", offerDiscountPercent=" + offerDiscountPercent + ", offerDescription="
				+ offerDescription + ", isExpired=" + isExpired + ", productList=" + productList + ", currency="
				+ currency + ", offerCurrencyDescription=" + offerCurrencyDescription + "]";
	}
	
}
