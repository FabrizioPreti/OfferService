package it.worldpay.faz.offerservice.utility;

public class Paths {
	
	//It verifies that the service is up and running
	public static final String CHECK = "/check";
	
	//ROOT for endpoints v1
	public static final String V1 = "/v1";
	
	//OfferController GET POST PUT
	public static final String V1_OFFERS = V1 + "/offers";
	public static final String V1_OFFERS_ID = V1_OFFERS + "/offer/{id}";
	//OfferController DELETE
	public static final String V1_OFFERS_DELETE = V1_OFFERS + "/offer";
	
	//ProductController GET POST PUT
	public static final String V1_PRODUCTS = V1 + "/products";
	public static final String V1_PRODUCTS_ID = V1_PRODUCTS + "/product/{id}";
	//ProductController DELETE
	public static final String V1_PRODUCTS_DELETE = V1_PRODUCTS + "/product";
	
	public static final String V1_CURRENCIES =  V1 + "/currency";

}
