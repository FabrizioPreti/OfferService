package it.worldpay.faz.offerservice.service;

import java.util.List;

import it.worldpay.faz.offerservice.dto.ProductDTO;

public interface ProductService {
	
	public List<ProductDTO> getAllProducts() throws Exception;
	public ProductDTO getProductById(String productId) throws Exception;
	public void createProduct(ProductDTO productDTO) throws Exception;
	public ProductDTO updateProduct(ProductDTO productDTO) throws Exception;
	public void deleteProduct(ProductDTO productDTO) throws Exception;

}
