package it.worldpay.faz.offerservice.configuration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import it.worldpay.faz.offerservice.model.CurrencyOffer;
import it.worldpay.faz.offerservice.model.Offer;
import it.worldpay.faz.offerservice.model.Product;
import it.worldpay.faz.offerservice.repository.CurrencyOfferRepository;
import it.worldpay.faz.offerservice.repository.OfferRepository;
import it.worldpay.faz.offerservice.repository.ProductRepository;

@Component
public class DBInitConfiguration implements ApplicationRunner {
	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private CurrencyOfferRepository currencyOfferRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		List<CurrencyOffer> currencyList = currencyOfferRepository.findAll();
		if(currencyList.isEmpty()) {
			insertCurrency();
		}
		
		List<Offer> offerList = offerRepository.findAll();
		if(offerList.isEmpty()) {
			insertOffer();
		}
		
		List<Product> productList = productRepository.findAll();
		if(productList.isEmpty()) {
			insertProduct();
		}
		
	}

	
	private void insertProduct() {
		
		List<Product> listProducts = new ArrayList<>();
		listProducts.add(new Product("1-PROD-cddf-48cd-9ed1-1b754129c0c1", "Chianti", "Red wine to enjoy yor meals", 
					new BigDecimal(25.0), new Offer("0-DEFAULT-OFFER"), true, new BigDecimal(6.25)));
		
		listProducts.add(new Product("2-PROD-cddf-48dd-9ed1-1b754129c0c2", "Amarone", "A special wine", 
					new BigDecimal(40.0), new Offer("1-OFFER-cddf-48dd-9ed1-1b754129c0c2"), true, new BigDecimal(10.0)));
		
		listProducts.add(new Product("3-PROD-cddf-48dd-9ed1-1b754129c0c3", "Brunello di Montalcino", "A deliciuous wine", 
				new BigDecimal(33.0), new Offer("2-OFFER-cddf-48dd-9ed1-1b754129c0c3"), true, new BigDecimal(3.3)));
		
		listProducts.add(new Product("4-PROD-cddf-48dd-9ed1-1b754129c0c4", "Fiano di Avellino", "A deliciuous white wine", 
				new BigDecimal(24.0), new Offer("3-OFFER-cddf-48dd-9ed1-1b754129c0c4"), true, new BigDecimal(6.0)));
		
		productRepository.saveAll(listProducts);
	}
	
	private void insertOffer() throws ParseException {
		
		List<Offer> listOffers = new ArrayList<>();
		listOffers.add(new Offer("0-DEFAULT-OFFER", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("9999-12-31 11:00:00"), 
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1970-01-01 00:00:00"), 0, 
							"DEFAULT OFFER", false, new ArrayList<Product>(), 
							new CurrencyOffer("0-DEFAULT-CURR-GBP"), "GBP"));
		
		listOffers.add(new Offer("1-OFFER-cddf-48dd-9ed1-1b754129c0c2", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-10-31 11:00:00"), 
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-10-29 11:00:00"), 10, 
							"Happy Friday", false, new ArrayList<Product>(), 
							new CurrencyOffer("1-CURR-cdcf-48dd-6hd1-1b754129c0c1"), "EUR"));
		
		listOffers.add(new Offer("2-OFFER-cddf-48dd-9ed1-1b754129c0c3", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-10-31 11:00:00"), 
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-10-29 11:00:00"), 50, 
							"Hot Deal", false, new ArrayList<Product>(), 
							new CurrencyOffer("2-CURR-cdcf-48dd-6hd1-1b754129c0c2"), "CAD"));
		
		listOffers.add(new Offer("3-OFFER-cddf-48dd-9ed1-1b754129c0c4", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-10-24 11:00:00"), 
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-10-23 11:00:00"), 25, 
							"Autumn Sale", false, new ArrayList<Product>(), 
							new CurrencyOffer("3-CURR-cdcf-48dd-6hd1-1b754129c0c3"), "USD"));
		
		offerRepository.saveAll(listOffers);
	}
	
	private void insertCurrency() {
		List<CurrencyOffer> listCurrencyOffer = new ArrayList<>();
		
		listCurrencyOffer.add(new CurrencyOffer("0-DEFAULT-CURR-GBP", "GBP", new BigDecimal(1.0))); 
													
		listCurrencyOffer.add(new CurrencyOffer("1-CURR-cdcf-48dd-6hd1-1b754129c0c1", "EUR", new BigDecimal(1.16)));
		listCurrencyOffer.add(new CurrencyOffer("2-CURR-cdcf-48dd-6hd1-1b754129c0c2", "CAD", new BigDecimal(1.70)));
		listCurrencyOffer.add(new CurrencyOffer("3-CURR-cdcf-48dd-6hd1-1b754129c0c3", "USD", new BigDecimal(1.30)));
		
		currencyOfferRepository.saveAll(listCurrencyOffer);
	}

}
