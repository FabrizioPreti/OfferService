package it.worldpay.faz.offerservice.dummy;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import io.vavr.Tuple2;
import it.worldpay.faz.offerservice.dto.BaseCurrencyDTO;
import it.worldpay.faz.offerservice.dto.CurrencyOfferDTO;
import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.dto.ProductDTO;
import it.worldpay.faz.offerservice.dto.RatesDTO;
import it.worldpay.faz.offerservice.model.CurrencyOffer;
import it.worldpay.faz.offerservice.model.Offer;
import it.worldpay.faz.offerservice.model.Product;

@Component
public class DummyFactoryImpl implements DummyFactory{
	
	@Override
	public BaseCurrencyDTO getAllDummyBaseCurrencyDTO() {
		
		RatesDTO rates = new RatesDTO();
		rates.setCAD("1.95");
		rates.setEUR("1.16");
		rates.setUSD("1.70");
		
		BaseCurrencyDTO currencyOfferDTO = new BaseCurrencyDTO();
		Date date = new Date();
		currencyOfferDTO.setBase("GBP");
		currencyOfferDTO.setDate(date);
		currencyOfferDTO.setRates(rates);
		
		return currencyOfferDTO;
	}

	@Override
	public ProductDTO getDummyProductDTO() {
		ProductDTO productDTO = new ProductDTO("1-PROD-cddf-48cd-9ed1-1b754129c0c1", 
											   "Chianti", "Red wine to enjoy yor meals", 
											   	new BigDecimal(25.0), 
											   	new OfferDTO("0-DEFAULT-OFFER"), 
											   	true, new BigDecimal(6.25));
		return productDTO;
	}

	@Override
	public OfferDTO getDummyOfferDTO() {
		
		Tuple2<Date, Date> tupla2Dates = getExpiringAndStartingDates("2019-12-31 11:00:00", "2019-10-29 11:00:00");
		
		Date offerExpiringDate = tupla2Dates._1;
		Date offerStartingDate = tupla2Dates._2;
		
		OfferDTO offerDTO = new OfferDTO("1-OFFER-cddf-48dd-9ed1-1b754129c0c2",
										 offerExpiringDate, offerStartingDate, 
										 10, "Happy Friday", false, new ArrayList<ProductDTO>(), 
										 new CurrencyOfferDTO("1-CURR-cdcf-48dd-6hd1-1b754129c0c1"),
										 "EUR");
		return offerDTO;
	}

	@Override
	public List<OfferDTO> getAllDummyOfferDTO() {
		
		List<OfferDTO> listOfferDTO = new ArrayList<>();
		
		Tuple2<Date, Date> tupla2Dates = getExpiringAndStartingDates("2019-12-31 11:00:00", "2019-10-29 11:00:00");
		
		Date offerExpiringDate = tupla2Dates._1;
		Date offerStartingDate = tupla2Dates._2;
		
		OfferDTO offerDTO1 = new OfferDTO("1-OFFER-cddf-48dd-9ed1-1b754129c0c2",
										 offerExpiringDate, offerStartingDate, 
										 10, "Happy Friday", false, new ArrayList<ProductDTO>(), 
										 new CurrencyOfferDTO("1-CURR-cdcf-48dd-6hd1-1b754129c0c1"),
										 "EUR");
		OfferDTO offerDTO2 = new OfferDTO("1-OFFER-cddf-48dd-9ed1-1b754129c0c2",
										offerExpiringDate, offerStartingDate, 
										10, "Hot DEal", false, new ArrayList<ProductDTO>(), 
										new CurrencyOfferDTO("1-CURR-cdcf-48dd-6hd1-1b754129c0c1"),
										"EUR");
		
		listOfferDTO.add(offerDTO1);
		listOfferDTO.add(offerDTO2);
		
		return listOfferDTO;
	}

	@Override
	public List<ProductDTO> getAllDummyProductDTO() {

		List<ProductDTO> listProducts = new ArrayList<>();
		ProductDTO productDTO1 = new ProductDTO("1-PROD-cddf-48cd-9ed1-1b754129c0c1", 
												"Chianti", "Red wine to enjoy yor meals", 
												new BigDecimal(25.0), new OfferDTO("0-DEFAULT-OFFER"), 
												true, new BigDecimal(6.25));
				
		ProductDTO productDTO2 = new ProductDTO("2-PROD-cddf-48dd-9ed1-1b754129c0c2", 
												"Amarone", "A special wine", 
												new BigDecimal(40.0), 
												new OfferDTO("1-OFFER-cddf-48dd-9ed1-1b754129c0c2"), 
												true, new BigDecimal(10.0));
		
		listProducts.add(productDTO1);
		listProducts.add(productDTO2);
		
		return listProducts;
	}

	@Override
	public CurrencyOffer getCurrencyOffer() {
		CurrencyOffer currencyOffer = new CurrencyOffer("1-CURR-cdcf-48dd-6hd1-1b754129c0c1", "EUR", 
														new BigDecimal(1.16));
		return currencyOffer;
	}

	@Override
	public Offer getDummyOffer() {
		Tuple2<Date, Date> tupla2Dates = getExpiringAndStartingDates("2019-12-31 11:00:00", "2019-10-29 11:00:00");
		
		Date offerExpiringDate = tupla2Dates._1;
		Date offerStartingDate = tupla2Dates._2;
		
		CurrencyOffer currnecy = new CurrencyOffer("1-CURR-cdcf-48dd-6hd1-1b754129c0c1");
		
		Offer offer1 = new Offer("1-OFFER-cddf-48dd-9ed1-1b754129c0c2",
				offerExpiringDate, offerStartingDate, 10, 
				"Happy Friday", false, new ArrayList<Product>(), 
				currnecy, "EUR");
		
		return offer1;
	}

	@Override
	public List<Offer> getAllDummyOffer() {
		
		Tuple2<Date, Date> tupla2Dates = getExpiringAndStartingDates("2019-12-31 11:00:00", "2019-10-29 11:00:00");
		
		Date offerExpiringDate = tupla2Dates._1;
		Date offerStartingDate = tupla2Dates._2;
		
		List<Offer> listOffers = new ArrayList<>();
		Offer offer1 = new Offer("1-OFFER-cddf-48dd-9ed1-1b754129c0c2",
								offerExpiringDate, offerStartingDate, 10, 
								"Happy Friday", false, new ArrayList<Product>(), 
								new CurrencyOffer("1-CURR-cdcf-48dd-6hd1-1b754129c0c1"), "EUR");
		
		Offer offer2 = new Offer("2-OFFER-cddf-48dd-9ed1-1b754129c0c3",
							offerExpiringDate, offerStartingDate, 50, 
							"Hot Deal", false, new ArrayList<Product>(), 
							new CurrencyOffer("2-CURR-cdcf-48dd-6hd1-1b754129c0c2"), "CAD");
		listOffers.add(offer1);
		listOffers.add(offer2);
				
		return listOffers;
	}
	
	@Override
	public Product getDummyProduct() {
		Product product = new Product("1-PROD-cddf-48cd-9ed1-1b754129c0c1", 
				   "Chianti", "Red wine to enjoy yor meals", 
				   	new BigDecimal(25.0), 
				   	new Offer("0-DEFAULT-OFFER"), 
				   	true, new BigDecimal(6.25));
		
		return product;
	}
	
	@Override
	public List<Product> getAllDummyProduct() {
		
		List<Product> listProducts = new ArrayList<>();
		Product product1 = new Product("1-PROD-cddf-48cd-9ed1-1b754129c0c1", 
												"Chianti", "Red wine to enjoy yor meals", 
												new BigDecimal(25.0), new Offer("0-DEFAULT-OFFER"), 
												true, new BigDecimal(6.25));
				
		Product product2 = new Product("2-PROD-cddf-48dd-9ed1-1b754129c0c2", 
												"Amarone", "A special wine", 
												new BigDecimal(40.0), 
												new Offer("1-OFFER-cddf-48dd-9ed1-1b754129c0c2"), 
												true, new BigDecimal(10.0));
		
		listProducts.add(product1);
		listProducts.add(product2);
		
		return listProducts;
	}
	
	public Tuple2<Date, Date> getExpiringAndStartingDates(String dateEnd, String dateStart){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date offerExpiringDate = null;
		Date offerStartingDate = null;
		try {
			offerExpiringDate = sdf.parse(dateEnd);
			offerStartingDate = sdf.parse(dateStart);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return new Tuple2<Date, Date>(offerExpiringDate, offerStartingDate);
	}

	@Override
	public CurrencyOfferDTO getCurrencyOfferDTO() {
		CurrencyOfferDTO currencyOfferDTO = 
				new CurrencyOfferDTO("1-CURR-cdcf-48dd-6hd1-1b754129c0c1", "EUR", new BigDecimal(1.16));
		
		return currencyOfferDTO;
	}
	
}
