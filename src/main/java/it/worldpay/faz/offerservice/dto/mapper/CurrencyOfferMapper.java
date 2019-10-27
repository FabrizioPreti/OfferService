package it.worldpay.faz.offerservice.dto.mapper;

import java.util.List;

import it.worldpay.faz.offerservice.dto.CurrencyOfferDTO;
import it.worldpay.faz.offerservice.model.CurrencyOffer;

/**
 * @author FP
 *
 */
public interface CurrencyOfferMapper {
	
	/**
	 * Maps from model to DTO
	 * @param currency
	 * @return currencyDTO
	 */
	public CurrencyOfferDTO fromModelToDTO(CurrencyOffer currency);
	
	/**
	 * Maps from DTO to model
	 * @param currencyDTO
	 * @return currency
	 */
	public CurrencyOffer toModelFromDTO(CurrencyOfferDTO currencyDTO);
	
	/**
	 * Maps from list of model to list of DTO
	 * @param listCurrency
	 * @return listCurrencyOfferDTO
	 */
	public List<CurrencyOfferDTO> mapListFromModelToDTO(List<CurrencyOffer> listCurrency);
	
	/**
	 * Maps from list of DTO to list of model
	 * @param listCurrencyDTO
	 * @return listCurrencyOffer
	 */
	public List<CurrencyOffer> mapListToModelFromDTO(List<CurrencyOfferDTO> listCurrencyDTO);
	
}
