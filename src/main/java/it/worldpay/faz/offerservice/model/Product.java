package it.worldpay.faz.offerservice.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author FPreti
 *
 */
@Entity(name="PRODUCT")
public class Product implements Serializable{
	
	private static final long serialVersionUID = 6731827270463363746L;
	
	@Id
	@Column(name = "PRODUCT_ID")
	private String productId;
	
	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	@Column(name = "PRODUCT_DESCR")
	private String productDescription;
	
	@Column(name = "PRODUCT_PRICE")
	private BigDecimal productPrice;
	
	@ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "OFFER_ID")
	private Offer offer;
	
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;
	
	@Column(name = "DISCOUNT_PRICE")
	private BigDecimal productDiscountedPrice;
	
	public Product() {}
	
	public Product(String productId, String productName, String productDescription, 
			BigDecimal productPrice, Offer offer, Boolean isActive, BigDecimal productDiscountedPrice) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.offer = offer;
		this.isActive = isActive;
		this.productDiscountedPrice = productDiscountedPrice;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getProductDiscountedPrice() {
		return productDiscountedPrice;
	}

	public void setProductDiscountedPrice(BigDecimal productDiscountedPrice) {
		this.productDiscountedPrice = productDiscountedPrice;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productDescription="
				+ productDescription + ", productPrice=" + productPrice + ", offer=" + offer + ", isActive=" + isActive
				+ ", productDiscountedPrice=" + productDiscountedPrice + "]";
	}

}
