package it.worldpay.faz.offerservice.controller.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.worldpay.faz.offerservice.dto.ProductDTO;
import it.worldpay.faz.offerservice.service.ProductService;
import it.worldpay.faz.offerservice.utility.Paths;

@CrossOrigin(origins="*")
@RestController
public class ProductController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService produtcService;
	
	@GetMapping(path = Paths.V1_PRODUCTS)
	public ResponseEntity<List<ProductDTO>> getAllProducts() throws Exception{

		List<ProductDTO> listProductDTO = produtcService.getAllProducts();
		
		log.info("getAllProducts() {}", listProductDTO.size());
		return ResponseEntity.ok(listProductDTO);
	}
	
	@GetMapping(path = Paths.V1_PRODUCTS_ID)
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") String productId) throws Exception{
		
		ProductDTO productDTO = produtcService.getProductById(productId);
		
		log.info("getProduct() {}", productDTO.toString());
		return ResponseEntity.ok(productDTO);
	}
	
	@PostMapping(path = Paths.V1_PRODUCTS)
	public ResponseEntity<HttpStatus> createProduct(@RequestBody ProductDTO productDTO) throws Exception{
		
		produtcService.createProduct(productDTO);
		
		log.info("createProduct() {}", productDTO.toString());
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	@PutMapping(path = Paths.V1_PRODUCTS)
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) throws Exception{
		
		ProductDTO dtoProduct = produtcService.updateProduct(productDTO);
		
		log.info("updateProduct() {}", productDTO.toString());
		return ResponseEntity.ok(dtoProduct);
	}
	
	@PutMapping(path = Paths.V1_PRODUCTS_DELETE)
	public ResponseEntity<HttpStatus> deleteProduct(@RequestBody ProductDTO productDTO) throws Exception{
		
		produtcService.deleteProduct(productDTO);
		
		log.info("deleteProduct() {}", productDTO.toString());
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

}
