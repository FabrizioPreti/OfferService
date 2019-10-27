package it.worldpay.faz.offerservice.dto.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.dto.ProductDTO;
import it.worldpay.faz.offerservice.dto.mapper.ProductMapper;
import it.worldpay.faz.offerservice.model.Offer;
import it.worldpay.faz.offerservice.model.Product;

@Component
public class ProductMapperImpl implements ProductMapper{
	
	@Override
	public ProductDTO fromModelToDTO(Product product) {
		
		return new ProductDTO(
				product.getProductId(),
				product.getProductName(),
				product.getProductDescription(),
				product.getProductPrice(),
				new OfferDTO(product.getOffer().getOfferId()),
				product.getIsActive(),
				product.getProductDiscountedPrice()
				);
	}
	
	@Override
	public Product toModelFromDTO(ProductDTO productDTO) {
		
		return new Product(
				productDTO.getProductId(),
				productDTO.getProductName(),
				productDTO.getProductDescription(),
				productDTO.getProductPrice(),
				new Offer(productDTO.getOffer().getOfferId()),
				productDTO.getIsActive(),
				productDTO.getProductDiscountedPrice()
				);
	}
	
	@Override
	public List<ProductDTO> mapListFromModelToDTO(List<Product> listProduct){
		return listProduct.stream().map(product -> fromModelToDTO(product)).collect(Collectors.toList());
	}
	
	@Override
	public List<Product> mapListToModelFromDTO(List<ProductDTO> listProductDTO){
		return listProductDTO.stream().map(productDTO -> toModelFromDTO(productDTO)).collect(Collectors.toList());
	}
}
