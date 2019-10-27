package it.worldpay.faz.offerservice.dto.mapper;

import java.util.List;

import it.worldpay.faz.offerservice.dto.ProductDTO;
import it.worldpay.faz.offerservice.model.Product;

/**
 * @author FP
 *
 */
public interface ProductMapper {
	
	/**
	 * Maps from model to DTO
	 * @param product
	 * @return productDTO
	 */
	public ProductDTO fromModelToDTO(Product product);
	
	/**
	 * Maps from DTO to model
	 * @param productDTO
	 * @return product
	 */
	public Product toModelFromDTO(ProductDTO productDTO);
	
	/**
	 * Maps from list of model to list of DTO
	 * @param listProduct
	 * @return listProductDTO
	 */
	public List<ProductDTO> mapListFromModelToDTO(List<Product> listProduct);
	
	/**
	 * Maps from list of DTO to list of model
	 * @param listProductDTO
	 * @return listProduct
	 */
	public List<Product> mapListToModelFromDTO(List<ProductDTO> listProductDTO);

}
