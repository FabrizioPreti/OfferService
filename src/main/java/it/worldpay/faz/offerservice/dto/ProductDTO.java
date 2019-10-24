package it.worldpay.faz.offerservice.dto;

import java.math.BigDecimal;

public class ProductDTO {
	
	private String productId;
	private String productName;
	private String productDescription;
	private BigDecimal productPrice;
	private OfferDTO offer;
	private Boolean isActive;
	private BigDecimal productDiscountedPrice;
	
	public ProductDTO() {}
	
	public ProductDTO(String productId, String productName, String productDescription, 
			BigDecimal productPrice, OfferDTO offer, Boolean isActive, BigDecimal productDiscountedPrice) {
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

	public OfferDTO getOffer() {
		return offer;
	}

	public void setOffer(OfferDTO offer) {
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
		return "ProductDTO [productId=" + productId + ", productName=" + productName + ", productDescription="
				+ productDescription + ", productPrice=" + productPrice + ", offer=" + offer + ", isActive=" + isActive
				+ ", productDiscountedPrice=" + productDiscountedPrice + "]";
	}
	
}
