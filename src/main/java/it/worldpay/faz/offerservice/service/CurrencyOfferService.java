package it.worldpay.faz.offerservice.service;

import it.worldpay.faz.offerservice.dto.BaseCurrencyDTO;
import it.worldpay.faz.offerservice.dto.CurrencyOfferDTO;
import it.worldpay.faz.offerservice.exception.ResourceNotFoundException;
import it.worldpay.faz.offerservice.exception.ServerNotAvailableException;

public interface CurrencyOfferService {
	
	public BaseCurrencyDTO getAllCurrencyFromAPI() throws ServerNotAvailableException;
	
	public void currencyScheduledUpdate() throws ServerNotAvailableException;
	
	public CurrencyOfferDTO findCurrencyById(String uuid) throws ResourceNotFoundException;

}
