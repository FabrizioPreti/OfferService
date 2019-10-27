package it.worldpay.faz.offerservice.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author FPreti
 *
 */
@Entity(name="OFFER")
public class Offer implements Serializable{

	private static final long serialVersionUID = -2517863847562585305L;
	
	
	@Id
	@Column(name = "OFFER_ID")
	private String offerId;
	
	@Column(name = "OFFER_EXPIRING_DATE")
	private Date offerExpiringDate;
	
	@Column(name = "OFFER_STARTING_DATE")
	private Date offerStartingDate;
	
	@Column(name = "OFFER_DISCOUNT")
	private Integer offerDiscountPercent;
	
	@Column(name = "OFFER_DESCRIPTION")
	private String offerDescription;
	
	@Column(name = "IS_EXPIRED")
	private boolean isExpired;
	
	@OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Product> productList;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	private CurrencyOffer currency;
	
	@Column(name = "CURRENCY_DESCR")
	private String offerCurrencyDescription;
	
	
	public Offer(){}
	
	public Offer(String offerId) {
		this.offerId = offerId;
	}

	public Offer(String offerId, Date offerExpiringDate, Date offerStartingDate, 
			Integer offerDiscountPercent, String offerDescription, boolean isExpired, 
			List<Product> productList, CurrencyOffer currency, String offerCurrencyDescription) {
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

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public CurrencyOffer getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyOffer currency) {
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
		return "Offer [offerId=" + offerId + ", offerExpiringDate=" + offerExpiringDate + ", offerStartingDate="
				+ offerStartingDate + ", offerDiscountPercent=" + offerDiscountPercent + ", offerDescription="
				+ offerDescription + ", isExpired=" + isExpired + ", productList=" + productList + ", currency="
				+ currency + ", offerCurrencyDescription=" + offerCurrencyDescription + "]";
	}
	
}
