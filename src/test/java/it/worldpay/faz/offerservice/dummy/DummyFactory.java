package it.worldpay.faz.offerservice.dummy;

import java.util.List;

import it.worldpay.faz.offerservice.dto.BaseCurrencyDTO;
import it.worldpay.faz.offerservice.dto.CurrencyOfferDTO;
import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.dto.ProductDTO;
import it.worldpay.faz.offerservice.model.CurrencyOffer;
import it.worldpay.faz.offerservice.model.Offer;
import it.worldpay.faz.offerservice.model.Product;

public interface DummyFactory {
	
	public BaseCurrencyDTO getAllDummyBaseCurrencyDTO();
	
	public CurrencyOffer getCurrencyOffer();
	
	public CurrencyOfferDTO getCurrencyOfferDTO();
	
	public ProductDTO getDummyProductDTO();
	
	public List<ProductDTO> getAllDummyProductDTO();
	
	public Product getDummyProduct();
	
	public List<Product> getAllDummyProduct();

	public OfferDTO getDummyOfferDTO();

	public List<OfferDTO> getAllDummyOfferDTO();

	public Offer getDummyOffer();
	
	public List<Offer> getAllDummyOffer();
	
	
}
