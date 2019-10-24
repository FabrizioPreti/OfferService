package it.worldpay.faz.offerservice.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import it.worldpay.faz.offerservice.dto.CurrencyOfferDTO;
import it.worldpay.faz.offerservice.model.CurrencyOffer;



public class CurrencyOfferMapper {
	
	public static CurrencyOfferDTO fromModelToDTO(CurrencyOffer currency) {
		
		return new CurrencyOfferDTO(
				currency.getCurrencyId(),
				currency.getCurrencyDescription(),
				currency.getCurrencyRate()
				);
	}

	public static CurrencyOffer toModelFromDTO(CurrencyOfferDTO currencyDTO) {
		
		return new CurrencyOffer(
				currencyDTO.getCurrencyId(),
				currencyDTO.getCurrencyDescription(),
				currencyDTO.getCurrencyRate()
				);
	}

	public static List<CurrencyOfferDTO> mapListFromModelToDTO(List<CurrencyOffer> listCurrency){
		return listCurrency.stream().map(CurrencyOfferMapper::fromModelToDTO).collect(Collectors.toList());
	}

	public static List<CurrencyOffer> mapListToModelFromDTO(List<CurrencyOfferDTO> listCurrencyDTO){
		return listCurrencyDTO.stream().map(CurrencyOfferMapper::toModelFromDTO).collect(Collectors.toList());
	}

}
