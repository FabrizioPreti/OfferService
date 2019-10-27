package it.worldpay.faz.offerservice.dto.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.worldpay.faz.offerservice.dto.CurrencyOfferDTO;
import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.dto.mapper.OfferMapper;
import it.worldpay.faz.offerservice.dto.mapper.ProductMapper;
import it.worldpay.faz.offerservice.model.CurrencyOffer;
import it.worldpay.faz.offerservice.model.Offer;
import it.worldpay.faz.offerservice.model.Product;

@Component
public class OfferMapperImpl implements OfferMapper{
	
	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public OfferDTO fromModelToDTO(Offer offer) {
		
		return new OfferDTO(
				offer.getOfferId(),
				offer.getOfferExpiringDate(), 
				offer.getOfferStartingDate(), 
				offer.getOfferDiscountPercent(), 
				offer.getOfferDescription(), 
				offer.isExpired(), 
				offer.getProductList().stream()
					.map(product ->
						productMapper.fromModelToDTO(product))
					.collect(Collectors.toList()), 
					new CurrencyOfferDTO(offer.getCurrency().getCurrencyId()),
				offer.getOfferCurrencyDescription()
				);
	}
	
	@Override
	public Offer toModelFromDTO(OfferDTO offerDTO) {
		
		return new Offer(
				offerDTO.getOfferId(),
				offerDTO.getOfferExpiringDate(), 
				offerDTO.getOfferStartingDate(), 
				offerDTO.getOfferDiscountPercent(), 
				offerDTO.getOfferDescription(), 
				offerDTO.isExpired(), 
				offerDTO.getProductList().stream()
				  .map(productDTO -> {
					  productDTO.setOffer(new OfferDTO(offerDTO.getOfferId()));
					  Product product = productMapper.toModelFromDTO(productDTO);
					  return product;
				  }).collect(Collectors.toList()),
				  new CurrencyOffer(offerDTO.getCurrency().getCurrencyId()),
				  offerDTO.getOfferCurrencyDescription()
				);
	}
	
	@Override
	public List<OfferDTO> mapListFromModelToDTO(List<Offer> listOffer){
		return listOffer.stream().map(offer -> fromModelToDTO(offer)).collect(Collectors.toList());
	}
	
	@Override
	public List<Offer> mapListToModelFromDTO(List<OfferDTO> listOfferDTO){
		return listOfferDTO.stream().map(offerDTO -> toModelFromDTO(offerDTO)).collect(Collectors.toList());
	}
}
