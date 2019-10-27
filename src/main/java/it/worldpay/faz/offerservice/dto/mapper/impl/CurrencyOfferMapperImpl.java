package it.worldpay.faz.offerservice.dto.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import it.worldpay.faz.offerservice.dto.CurrencyOfferDTO;
import it.worldpay.faz.offerservice.dto.mapper.CurrencyOfferMapper;
import it.worldpay.faz.offerservice.model.CurrencyOffer;

@Component
public class CurrencyOfferMapperImpl implements CurrencyOfferMapper{

	
	@Override
	public CurrencyOfferDTO fromModelToDTO(CurrencyOffer currency) {
		
		return new CurrencyOfferDTO(
				currency.getCurrencyId(),
				currency.getCurrencyDescription(),
				currency.getCurrencyRate()
				);
	}
	
	@Override
	public CurrencyOffer toModelFromDTO(CurrencyOfferDTO currencyDTO) {
		
		return new CurrencyOffer(
				currencyDTO.getCurrencyId(),
				currencyDTO.getCurrencyDescription(),
				currencyDTO.getCurrencyRate()
				);
	}
	
	@Override
	public List<CurrencyOfferDTO> mapListFromModelToDTO(List<CurrencyOffer> listCurrency){
		return listCurrency.stream().map(currency -> fromModelToDTO(currency)).collect(Collectors.toList());
	}
	
	@Override
	public List<CurrencyOffer> mapListToModelFromDTO(List<CurrencyOfferDTO> listCurrencyDTO){
		return listCurrencyDTO.stream().map(currencyOfferDTO -> toModelFromDTO(currencyOfferDTO)).collect(Collectors.toList());
	}
	
}
