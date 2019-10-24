package it.worldpay.faz.offerservice.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import it.worldpay.faz.offerservice.dto.CurrencyOfferDTO;
import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.model.Offer;
import it.worldpay.faz.offerservice.model.Product;

public class OfferMapper {
	
	public static OfferDTO fromModelToDTO(Offer offer) {
		
		return new OfferDTO(
				offer.getOfferId(),
				offer.getOfferExpiringDate(), 
				offer.getOfferStartingDate(), 
				offer.getOfferDiscountPercent(), 
				offer.getOfferDescription(), 
				offer.isExpired(), 
				offer.getProductList().stream()
					.map(product ->
						ProductMapper.fromModelToDTO(product))
					.collect(Collectors.toList()), 
					new CurrencyOfferDTO(offer.getCurrency().getCurrencyId()),
				offer.getOfferCurrencyDescription()
				);
	}
	
	public static Offer toModelFromDTO(OfferDTO offerDTO) {
		
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
					  Product product = ProductMapper.toModelFromDTO(productDTO);
					  return product;
				  }).collect(Collectors.toList()),
				  CurrencyOfferMapper.toModelFromDTO(offerDTO.getCurrency()),
				  offerDTO.getOfferCurrencyDescription()
				);
	}

	public static List<OfferDTO> mapListFromModelToDTO(List<Offer> listOffer){
		return listOffer.stream().map(OfferMapper::fromModelToDTO).collect(Collectors.toList());
	}
	
	public static List<Offer> mapListToModelFromDTO(List<OfferDTO> listOffer){
		return listOffer.stream().map(OfferMapper::toModelFromDTO).collect(Collectors.toList());
	}
}
