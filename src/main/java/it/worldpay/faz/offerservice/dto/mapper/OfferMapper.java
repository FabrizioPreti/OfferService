package it.worldpay.faz.offerservice.dto.mapper;

import java.util.List;

import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.model.Offer;

/**
 * @author FP
 *
 */
public interface OfferMapper {
	
	/**
	 * Maps from model to DTO
	 * @param offer
	 * @return offerDTO
	 */
	public OfferDTO fromModelToDTO(Offer offer);
	
	/**
	 * Maps from DTO to model
	 * @param offerDTO
	 * @return offer
	 */
	public Offer toModelFromDTO(OfferDTO offerDTO);

	/**
	 * Maps from list of model to list of DTO
	 * @param listOffer
	 * @return listOfferDTO
	 */
	public List<OfferDTO> mapListFromModelToDTO(List<Offer> listOffer);
	
	/**
	 * Maps from list of DTO to list of model
	 * @param listOfferDTO
	 * @return listOffer
	 */
	public List<Offer> mapListToModelFromDTO(List<OfferDTO> listOfferDTO);
	
}
