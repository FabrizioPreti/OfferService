package it.worldpay.faz.offerservice.service;

import java.util.List;

import it.worldpay.faz.offerservice.dto.OfferDTO;

public interface OfferService {
	
	public List<OfferDTO> getAllOffers() throws Exception;
	public OfferDTO getOfferById(String offerId) throws Exception;
	public void createOffer(OfferDTO offerDTO) throws Exception;
	public OfferDTO updateOffer(OfferDTO offerDTO) throws Exception;
	public void deleteOffer(OfferDTO offerDTO) throws Exception;

}
