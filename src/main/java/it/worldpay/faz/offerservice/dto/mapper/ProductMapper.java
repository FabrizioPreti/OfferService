package it.worldpay.faz.offerservice.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.dto.ProductDTO;
import it.worldpay.faz.offerservice.model.Offer;
import it.worldpay.faz.offerservice.model.Product;

public class ProductMapper {
	
	public static ProductDTO fromModelToDTO(Product product) {
		
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
	
	public static Product toModelFromDTO(ProductDTO productDTO) {
		
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
	
	public static List<ProductDTO> mapListFromModelToDTO(List<Product> listProduct){
		return listProduct.stream().map(ProductMapper::fromModelToDTO).collect(Collectors.toList());
	}
	
	public static List<Product> mapListToModelFromDTO(List<ProductDTO> listProduct){
		return listProduct.stream().map(ProductMapper::toModelFromDTO).collect(Collectors.toList());
	}
}
