package it.worldpay.faz.offerservice.service;

import java.util.List;

import it.worldpay.faz.offerservice.dto.ProductDTO;
import it.worldpay.faz.offerservice.exception.DuplicateResourceException;
import it.worldpay.faz.offerservice.exception.ResourceNotFoundException;

public interface ProductService {
	
	public List<ProductDTO> getAllProducts() throws ResourceNotFoundException;
	
	public ProductDTO getProductById(String productId) throws ResourceNotFoundException;
	
	public void createProduct(ProductDTO productDTO) throws DuplicateResourceException, Exception;
	
	public ProductDTO updateProduct(ProductDTO productDTO) throws Exception;
	
	public void deleteProduct(ProductDTO productDTO) throws Exception;

}
