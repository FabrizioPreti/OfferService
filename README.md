# Worldpay Offer Service
A simple Spring Boot REST API for a merchant to manage his offers. 
Every Offer contains a shopper friendly descriptions, a price, a defined currency, a list to products and their description. Every Offer has period of time defined and an expire date.

## Assignment
You are required to create a simple RESTful software service that will:
* Allow a merchant to create a new simple offer. 
* Offers, once created, may be queried. After the period of time defined on the offer it should expire and further requests to query the offer should reflect that somehow. 
* Before an offer has expired users may cancel it.

## Implemented requirements: 
### Endpoints
The manage of the unique identifier (UID) has been managed through the generation of a random id (UUID). So, at the time of creation then the id field is empty.
Offer Endpoints:
	- endpoint for creating a new simple offer, with or without products association and  	
	  with existing currency or associated with an "empty" default currency.
	- endpoint for modify and/or cancel offer, before an offer has expired.
	- endpoint for consult one or all existing offers, both expired and not expired.
	- endpoint for manually expire an Offer (update and/or logical delete, before an offer has expired).
	- before an offer has expired  "merchant" may cancel or modify it, after that it is no longer possible.
Product Endpoints:
	- endpoint to create products associated with existing offers. 
	- endpoint for modify and/or delete active products, consult one or all products both active and inactive.
CurrencyOffer Endpoints:
	- endpoint for consulting all existing currencies associeted with offers. The currency Service call another external service to recive the update currency rates.

### Automatic Offer Expiration 
The automatic offers expiration at is managed at the application start , when the http get methods are called for the first time.
	
### Automatic discount product price associeted with currency 
The price of the product is managed by calculating the discount percentage of the associated offer and in relation to its reference currency. The final calculation is visible in the product discounted price
 
### Automatic Currency update of exchange rates scheduled task 
Through the Currency Service an external Rest service is called at the following url "https://api.exchangeratesapi.io/latest?base=GBP", which returns the exchange values of a series of currencies updated  based on the reference currency, which in the Offer Service is GBP. Once the data is received, it is manipulated to return the discounted prices based on the currency of each product. 
A schedule takes care of updating the relative exchange rate values on the db in real time (actually it's set every 10 minutes).

## Toolset
	- Spring Boot (embedded Tomcat)
	- Spring MVC
	- Spring Data JPA
	- Hibernate
	- H2 DB (in-memory database)
	- Maven
	- Git
	- Junit
	- Mockito
	- SpringBootTest

## Prerequisites
	- Java Runtime 1.8
	- GIT
	- Maven
	- Spring Tool Suite 4 or Eclipse IDE Oxygen, if you want to    	
	  build/run/debug source code easier.

## Quick start
Use the following command:
	- `git clone - https://github.com/FabrizioPreti/OfferService.git`
	- `cd /offerservice`
	- `mvn clean install`
	- the embedded Tomcat starts at `http://localhost:8102`
	- H2 DB console is available on `http://localhost:8102/offerservice/h2/`. 
	- Access with: url=jdbc:h2:mem:testdb, user=sa (no password).
	- use Postman App to test the Rest API in a very simple way. 
	- postman collections are in the postaman_collections folder.

## Features
### Below Offer Service main structure :
#### CONFIGURATION 
	- Cors Configuration to manage cross-origin.
	- DB Init Configuration to pre-populate the db at startup.
	- Scheduled Configuration to manage automatic currency updates on the db.

#### 3 CONTROLLERS
	- Offer Controller -> endpoints (get all offers/ get offer/  create offe / update offer/ delete offer)
	(One to Many relationship with Product). 			 
	- Product Controller -> endpoints (get all products/ get product/ create product/ update product/ 
	  delete product).
	- Currency Offer Controller -> endponints(get all currencies). 
		
#### 3 MODELS:
	- Offer (offerId, offerExpiringDate, offerStartingDate, offerDiscountPercent, offerDescription, 	
	         isExpired, productList, currencyOffer, offerCurrencyDescription).
	- Product (productId, productName, productDescription, productPrice, offer, isActive, 
	           productDiscountPrice).
	- CurrencyOffer (currencyId, currencyDescription, currencyRate)

#### 3 SERVICES:
	- Offer Service -> layer to manage all kind of "merchants" business logic: 
		- create offers, associate offers with products and with currency, modify and / or cancel offers, 
		 expire active offers, consult one or all existing offers, both active and expired.
		- automatic expiration of offers managed at the time of application start (get and update Offers)
	- Product Service -> layer to manage Products:
		- create products associated with existing offers or associated with an "empty" default offer,  
		- modify and / or delete active products, consult one or all products both active and inactive.
	- CurrencyOffer Service -> layer to manage CurrencyOffer:
		- in addition to consulting the existing currencies, this service allows you to update in a 
		  scheduled way, all the currencies on the db.  
		
#### 3 REPOSITORIES  to manage data transfer with embedded db
	- Offer Repository  (OFFER Entity).
	- Product Repository  (PRODUCT Entity).
	- CurrencyOffer  (CURRENCY Entity).

#### 3 MAPPERS 
	- Offer Mapper, that converts Request OfferDTO objects to OfferModel objects and Response OfferModel 
	  objects to OfferDTO.
	- Product Mapper, that converts Request ProductDTO objects to ProductModel objects and Response 
	  ProductModel objects to ProductDTO.
	- CurrencyOffer Mapper, that converts Request CurrencyOfferDTO objects to CurrencyOfferModel objects  
	  and Response CurrencyOfferModel objects to CurrencyOfferDTO.

#### EXCEPTION HANDLING  the custom exception classes used is thrown for negative scenarios
	- DuplicateResourceException, Resource is alredy present
	- OfferDatesException, Offer dates are incorrect
	- OfferExpiredException, Offer is expired
	- OfferServiceGenericException, Generic Internal Error
	- ResourceNotFoundException, Resource Not Found
	- ServerNotAvailableException, The Server is not available at the moment
	
#### UNIT AND INTEGRATION TESTING
The unit tests involved:
		- Controllers, used JUnit 4 with Spring MVC with Spring WebMvcTest and MockMvc Object
		- Repositories, used JUnit 4 with used JPA with DataJpaTest that use an embedded in-memory database by default
		- Services, used JUnit 4 with used SpringBootTest and Mockito.

## Running OFFERS
SUMMARY:
* get all offers or get offer by id;
* create, update and/or  delete (before offer has expired) an offer with and without products and  currency associated.
* offer exception handling : 
	 	- Get an offer with wrong id
		- Create an offer Expired:
		- Create an offer whith date malformed:
		- Create a duplicate offer:
		- Update an offer Expired
		- Delete an offer Expired (just send the same json twice )
	
### How to test step by step via REST client 

---------- GET ALL OFFERS ----------
* GET http://localhost:8102/offerservice/v1/offers

---------- GET OFFER BY ID ----------
* GET http://localhost:8102/offerservice/v1/offers/offer/1-OFFER-cddf-48dd-9ed1-1b754129c0c2

---------- CREATE AN OFFER ----------
* POST http://localhost:8102/offerservice/v1/offers

		- JSON example without associated products :
{
   "offerId": "",
   "offerExpiringDate": "2019-12-30 23:00:00",
   "offerStartingDate": "2019-11-30 23:00:00",
   "offerDiscountPercent": 20,
   "offerDescription": "We want you!!! For this special offer!",
   "productList": [],
   "currency": {
       "currencyId": "0-DEFAULT-CURR-GBP"
   },
   "expired": false,
   "offerCurrencyDescription": "GBP"
}

		- JSON example with associated products :
{
   "offerId": "",
   "offerExpiringDate": "2019-12-30 23:00:00",
   "offerStartingDate": "2019-11-30 23:00:00",
   "offerDiscountPercent": 20,
   "offerDescription": "Have a sip!",
   "productList": [
           {
           "productId":"4-PROD-cddf-48dd-9ed1-1b754129c0c4",
           "productName":"Barbera",
           "productDescription":"Red wine",
           "productPrice":25,
           "isActive": true,
           "productDiscountedPrice":0
           }
       ],
   "currency": {
       "currencyId": "0-DEFAULT-CURR-GBP"
   },
   "expired": false,
   "offerCurrencyDescription": "GBP"
}

---------- UPDATE OFFER ----------
 PUT http://localhost:8102/offerservice/v1/offers

	- JSON example without associated products :
{
   "offerId": "2-OFFER-cddf-48dd-9ed1-1b754129c0c3",
   "offerExpiringDate": "2019-10-31 11:00:00",
   "offerStartingDate": "2019-10-29 11:00:00",
   "offerDiscountPercent": 50,
   "offerDescription": "Hot Deal but only if you have the money",
   "productList": [],
   "currency": {
       "currencyId": "2-CURR-cdcf-48dd-6hd1-1b754129c0c2",
       "currencyDescription": null,
       "currencyRate": null
   },
   "offerCurrencyDescription": "CAD",
   "expired": false
}

	- JSON example with associated products :
{
       "offerId": "1-OFFER-cddf-48dd-9ed1-1b754129c0c2",
       "offerExpiringDate": "2019-10-31 11:00:00",
       "offerStartingDate": "2019-10-29 11:00:00",
       "offerDiscountPercent": 10,
       "offerDescription": "Happy Friday To EvreyBody",
       "productList": [
           {
               "productId": "2-PROD-cddf-48dd-9ed1-1b754129c0c2",
               "productName": "Amarone",
               "productDescription": "A special wine",
               "productPrice": 40.00,
               "offer": {
                   "offerId": "1-OFFER-cddf-48dd-9ed1-1b754129c0c2",
                   "offerExpiringDate": null,
                   "offerStartingDate": null,
                   "offerDiscountPercent": null,
                   "offerDescription": null,
                   "productList": null,
                   "currency": null,
                   "offerCurrencyDescription": null,
                   "expired": false
               },
               "isActive": true,
               "productDiscountedPrice": 10.00
           }
       ],
       "currency": {
           "currencyId": "1-CURR-cdcf-48dd-6hd1-1b754129c0c1",
           "currencyDescription": null,
           "currencyRate": null
       },
       "offerCurrencyDescription": "EUR",
       "expired": false
   }

---------- DELETE OFFER---------- 
* PUT http://localhost:8102/offerservice/v1/offers/offer

		- JSON example without associated products (just send the same json twice to confirm that the offer is expire):
	
	{
       "offerId": "3-OFFER-cddf-48dd-9ed1-1b754129c0c4",
       "offerExpiringDate": "2019-10-31 11:00:00",
       "offerStartingDate": "2019-10-29 11:00:00",
       "offerDiscountPercent": 25,
       "offerDescription": "Autumn Sale",
       "productList": [],
       "currency": {
           "currencyId": "3-CURR-cdcf-48dd-6hd1-1b754129c0c3",
           "currencyDescription": null,
           "currencyRate": null
       },
       "offerCurrencyDescription": "USD",
       "expired": false
   }
   
		- JSON example with associated products: (just send the same json twice to confirm that the offer is expire):
{
       "offerId": "1-OFFER-cddf-48dd-9ed1-1b754129c0c2",
       "offerExpiringDate": "2019-10-31 11:00:00",
       "offerStartingDate": "2019-10-29 11:00:00",
       "offerDiscountPercent": 10,
       "offerDescription": "Happy Friday To EvreyBody",
       "productList": [
           {
               "productId": "2-PROD-cddf-48dd-9ed1-1b754129c0c2",
               "productName": "Amarone",
               "productDescription": "A special wine",
               "productPrice": 40.00,
               "offer": {
                   "offerId": "1-OFFER-cddf-48dd-9ed1-1b754129c0c2",
                   "offerExpiringDate": null,
                   "offerStartingDate": null,
                   "offerDiscountPercent": null,
                   "offerDescription": null,
                   "productList": null,
                   "currency": null,
                   "offerCurrencyDescription": null,
                   "expired": false
               },
               "isActive": true,
               "productDiscountedPrice": 10.00
           }
       ],
       "currency": {
           "currencyId": "1-CURR-cdcf-48dd-6hd1-1b754129c0c1",
           "currencyDescription": null,
           "currencyRate": null
       },
       "offerCurrencyDescription": "EUR",
       "expired": false
   }

---------- OFFER EXCEPTION HANDLING ----------
* GET http://localhost:8102/offerservice/v1/offers/offer/2-PROD-cddf-48dd-9ed1-1b754129c0c2

		- Get an offer with wrong id: 
Exception: "Offer not found."

* POST http://localhost:8102/offerservice/v1/offers/

		- Create an offer Expired: 
{
   "offerId": "",
   "offerExpiringDate": "2019-10-15 23:00:00",
   "offerStartingDate": "2019-10-10 23:00:00",
   "offerDiscountPercent": 20,
   "offerDescription": "We want you!!! For this special offer!",
   "productList": [],
   "currency": {
       "currencyId": "0-DEFAULT-CURR-GBP"
   },
   "expired": false,
   "offerCurrencyDescription": "GBP"
}

Exception: "Attention the offer dates result already expired."

	- Create an offer whith date malformed:
{
   "offerId": "",
   "offerExpiringDate": "2019-11-30 23:00:00",
   "offerStartingDate": "2019-12-30 23:00:00",
   "offerDiscountPercent": 20,
   "offerDescription": "We want you!!! For this special offer!",
   "productList": [],
   "currency": {
       "currencyId": "0-DEFAULT-CURR-GBP"
   },
   "expired": false,
   "offerCurrencyDescription": "GBP"
}
Exception: "Expiring date must be after starting date."

	- Create a duplicate offer:
{
   "offerId": "1-OFFER-cddf-48dd-9ed1-1b754129c0c2",
   "offerExpiringDate": "2019-12-30 23:00:00",
   "offerStartingDate": "2019-11-30 23:00:00",
   "offerDiscountPercent": 20,
   "offerDescription": "We want you!!! For this special offer!",
   "productList": [],
   "currency": {
       "currencyId": "0-DEFAULT-CURR-GBP"
   },
   "expired": false,
   "offerCurrencyDescription": "GBP"
}

Exception: "Offer already exist."

* PUT http://localhost:8102/offerservice/v1/offers

		- Update an offer Expired
	{
   "offerId": "2-OFFER-cddf-48dd-9ed1-1b754129c0c3",
   "offerExpiringDate": "2019-10-31 11:00:00",
   "offerStartingDate": "2019-10-29 11:00:00",
   "offerDiscountPercent": 50,
   "offerDescription": "Hot Deal but only if you have the money",
   "productList": [],
   "currency": {
       "currencyId": "2-CURR-cdcf-48dd-6hd1-1b754129c0c2",
       "currencyDescription": null,
       "currencyRate": null
   },
   "offerCurrencyDescription": "CAD",
   "expired": true
}
	
Exception: "Offer is alredy expired."

PUT http://localhost:8102/offerservice/v1/offers/offer

- Delete an offer Expired (just send the same json twice )
	{
       "offerId": "1-OFFER-cddf-48dd-9ed1-1b754129c0c2",
       "offerExpiringDate": "2019-10-31 11:00:00",
       "offerStartingDate": "2019-10-29 11:00:00",
       "offerDiscountPercent": 10,
       "offerDescription": "Happy Friday To EvreyBody",
       "productList": [
           {
               "productId": "2-PROD-cddf-48dd-9ed1-1b754129c0c2",
               "productName": "Amarone",
               "productDescription": "A special wine",
               "productPrice": 40.00,
               "offer": {
                   "offerId": "1-OFFER-cddf-48dd-9ed1-1b754129c0c2",
                   "offerExpiringDate": null,
                   "offerStartingDate": null,
                   "offerDiscountPercent": null,
                   "offerDescription": null,
                   "productList": null,
                   "currency": null,
                   "offerCurrencyDescription": null,
                   "expired": false
               },
               "isActive": true,
               "productDiscountedPrice": 10.00
           }
       ],
       "currency": {
           "currencyId": "1-CURR-cdcf-48dd-6hd1-1b754129c0c1",
           "currencyDescription": null,
           "currencyRate": null
       },
       "offerCurrencyDescription": "EUR",
       "expired": false
   }
   
Exception: "Offer is alredy expired."
   
## Running PRODUCTS
SUMMARY: 
* get all products or get product by id;
* create, update, delete a product with existing offer or with default offer "empty"
* product exception handling : 
	- Create a duplicate product:

### How to test step by step via REST client 

---------- GET ALL PRODUCTS----------
* GET http://localhost:8102/offerservice/v1/products

---------- GET PRODUCT BY ID ----------
* GET http://localhost:8102/offerservice/v1/products/product/3-PROD-cddf-48dd-9ed1-1b754129c0c3

---------- CREATE A PRODUCT ----------
* POST http://localhost:8102/offerservice/v1/products

		- JSON example with existing offer :
{
    "productId":"",
    "productName":"Greco di Tufo",
    "productDescription":"White wine",
    "productPrice":9,
    "offer":{"offerId":"1-OFFER-cddf-48dd-9ed1-1b754129c0c2"},
    "isActive": true,
    "productDiscountedPrice":0
}

	- JSON example with existing default "empty" offer:(the default offer id with GBP currency will be assigned)
{
    "productId":"",
    "productName":"Greco di Tufo",
    "productDescription":"White wine",
    "productPrice":9,
    "offer":{"offerId":""},
    "isActive": true,
   "productDiscountedPrice": 0
}

---------- UPDATE PRODUCT----------
* PUT http://localhost:8102/offerservice/v1/products

		- JSON example with existing offer:
	{
   "productId": "1-PROD-cddf-48cd-9ed1-1b754129c0c1",
   "productName": "Chianti",
   "productDescription": "Red wine to enjoy yor meals",
   "productPrice": 25.00,
   "offer": {
       "offerId": "0-DEFAULT-OFFER",
       "offerExpiringDate": null,
       "offerStartingDate": null,
       "offerDiscountPercent": null,
       "offerDescription": null,
       "productList": null,
       "currency": null,
       "offerCurrencyDescription": null,
       "expired": false
   },
   "isActive": true,
   "productDiscountedPrice": 6.25
}

---------- DELETE PRODUCT ----------
* PUT http://localhost:8102/offerservice/v1/products/product (just send the same json twice and "isActive" become false )

		- JSON example with existing offer: 
	{
   "productId": "1-PROD-cddf-48cd-9ed1-1b754129c0c1",
   "productName": "Brunello",
   "productDescription": "Red wine to enjoy yor meals",
   "productPrice": 25.00,
   "offer": {
       "offerId": "0-DEFAULT-OFFER",
       "offerExpiringDate": null,
       "offerStartingDate": null,
       "offerDiscountPercent": null,
       "offerDescription": null,
       "productList": null,
       "currency": null,
       "offerCurrencyDescription": null,
       "expired": false
   },
   "isActive": true,
   "productDiscountedPrice": 6.25
}
	
---------- PRODUCT EXCEPTION HANDLING ----------
* POST http://localhost:8102/offerservice/v1/products

- Create a duplicate product:
{
    "productId":"1-PROD-cddf-48cd-9ed1-1b754129c0c1",
    "productName":"Greco di Tufo",
    "productDescription":"White wine",
    "productPrice":9,
    "offer":{"offerId":"1-OFFER-cddf-48dd-9ed1-1b754129c0c2"},
    "isActive": true,
    "productDiscountedPrice":0
}

Exception: "Product already exist"

## Running CURRENCYOFFER

* get all currency;
 
---------- GET ALL CURRENCIES----------
* GET http://localhost:8102/offerservice/v1/currency

