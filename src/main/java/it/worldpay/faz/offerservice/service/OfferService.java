package it.worldpay.faz.offerservice.service;

import java.util.List;

import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.exception.DuplicateResourceException;
import it.worldpay.faz.offerservice.exception.OfferDatesException;
import it.worldpay.faz.offerservice.exception.OfferExpiredException;
import it.worldpay.faz.offerservice.exception.ResourceNotFoundException;
import it.worldpay.faz.offerservice.exception.ServerNotAvailableException;

public interface OfferService {
	
	public List<OfferDTO> getAllOffers() throws ResourceNotFoundException;
	
	public OfferDTO getOfferById(String offerId) throws ResourceNotFoundException;
	
	public void createOffer(OfferDTO offerDTO) throws DuplicateResourceException, OfferDatesException;
	
	public OfferDTO updateOffer(OfferDTO offerDTO) throws OfferDatesException, OfferExpiredException;
	
	public void deleteOffer(OfferDTO offerDTO) throws OfferDatesException, OfferExpiredException;
	
	public void offerScheduledUpdateExpire()  throws ServerNotAvailableException;

}
