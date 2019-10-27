package it.worldpay.faz.offerservice.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.worldpay.faz.offerservice.dto.CurrencyOfferDTO;
import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.dto.ProductDTO;
import it.worldpay.faz.offerservice.dto.mapper.ProductMapper;
import it.worldpay.faz.offerservice.exception.DuplicateResourceException;
import it.worldpay.faz.offerservice.exception.ResourceNotFoundException;
import it.worldpay.faz.offerservice.model.Product;
import it.worldpay.faz.offerservice.repository.ProductRepository;
import it.worldpay.faz.offerservice.service.CurrencyOfferService;
import it.worldpay.faz.offerservice.service.OfferService;
import it.worldpay.faz.offerservice.service.ProductService;
import it.worldpay.faz.offerservice.utility.CurrencyEnum;
import it.worldpay.faz.offerservice.utility.UtilConstants;


@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private CurrencyOfferService currencyOfferService;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public List<ProductDTO> getAllProducts() throws ResourceNotFoundException {
		log.info("getAllProducts()");
		
		List<ProductDTO> listProductDTO = productMapper
				.mapListFromModelToDTO(productRepository.findAll().stream().collect(Collectors.toList()));

		if(listProductDTO.size() == 0) {
			throw new ResourceNotFoundException("List is empty");
		}

		return listProductDTO;
	}
	
	@Override
	public ProductDTO getProductById(String productId) throws ResourceNotFoundException {
		log.info("getProductById() {} = ", productId);
		
		ProductDTO productDTO = productMapper
				.fromModelToDTO(Optional.ofNullable(productRepository.findByUUID(productId))
						.orElseThrow(() -> new ResourceNotFoundException("Product not found")));
		
		return productDTO;
	}

	@Override
	@Transactional
	public void createProduct(ProductDTO productDTO) throws DuplicateResourceException, Exception {
		log.info("createProduct() {} = ", productDTO.toString());
		
		String idOffer = productDTO.getOffer() != null && !productDTO.getOffer().getOfferId().equals("") ? 
													productDTO.getOffer().getOfferId() : UtilConstants.DEFAULT_OFFER_ID;
													
		OfferDTO offerDTO = offerService.getOfferById(idOffer);
		
		CurrencyOfferDTO currencyOffer = currencyOfferService.findCurrencyById(offerDTO.getCurrency().getCurrencyId());
		
		BigDecimal productPriceByExchange = productDTO.getProductPrice();
		
		//apply currency exchange to product price if currency is different from GBP
		if(!currencyOffer.getCurrencyDescription().equals(CurrencyEnum.GBP.toString())){
			productPriceByExchange = productPriceByExchange.multiply(currencyOffer.getCurrencyRate());
		}
		
		//apply discount on price
		BigDecimal productDicountedPrice = productPriceByExchange.subtract(productPriceByExchange
										.multiply(new BigDecimal(offerDTO.getOfferDiscountPercent()))
										.divide(new BigDecimal(UtilConstants.ONE_HUNDRED)))
										.setScale(2, RoundingMode.HALF_UP);
				
		productDTO.setOffer(offerDTO);
		productDTO.setProductDiscountedPrice(productDicountedPrice);
		
		/*
		 * The id generetion is been implemented using UUID.randomUUID().toString()
		 * to ensure uniqueness and to avoid the hard coding into jsons, during tests, of fake id 
		 * not genereted by the back-end. 
		 * This choise is due to the use of h2 embedded db that doesn't allow both Hibernate 
		 * strategies like AUTO or IDENTITY and properties mapping to manage pk fk reletions.
		 * This well documented problem is easily avoided by using a fully fledge RDBMS.
		 */
		if (productDTO.getProductId().equals("")) {
			String uuid = UUID.randomUUID().toString();
			productDTO.setProductId(uuid);
		}
		
		checkProductExist(productDTO);
		
		Product product = productMapper.toModelFromDTO(productDTO);
		log.info("createProduct() {} = ", productDTO.toString());
		productRepository.save(product);
	}

	@Override
	@Transactional
	public ProductDTO updateProduct(ProductDTO productDTO) throws Exception {
		log.info("updateProduct() {}", productDTO.toString());
		
		Product product = productMapper.toModelFromDTO(productDTO);
		
		productDTO = productMapper.fromModelToDTO(productRepository.save(product));
		
		return productDTO;

	}

	@Override
	@Transactional
	public void deleteProduct(ProductDTO productDTO) throws Exception {
		log.info("deleteProduct() {}", productDTO.toString());
		
		Product product = productMapper.toModelFromDTO(productDTO);
		product.setIsActive(false);
		
		productRepository.save(product);
	}
	
	private void checkProductExist(ProductDTO productDTO) throws DuplicateResourceException {
		log.info("checkProductExist()");
		
		Optional<Product> productExist = Optional.ofNullable(productRepository.findByUUID(productDTO.getProductId()));
		if(productExist.isPresent()) {
			throw new DuplicateResourceException("Product already exist");
		}
	}

}
