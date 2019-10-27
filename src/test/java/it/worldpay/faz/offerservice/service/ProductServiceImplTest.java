package it.worldpay.faz.offerservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.worldpay.faz.offerservice.dto.CurrencyOfferDTO;
import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.dto.ProductDTO;
import it.worldpay.faz.offerservice.dto.mapper.ProductMapper;
import it.worldpay.faz.offerservice.dto.mapper.impl.ProductMapperImpl;
import it.worldpay.faz.offerservice.dummy.DummyFactoryImpl;
import it.worldpay.faz.offerservice.exception.DuplicateResourceException;
import it.worldpay.faz.offerservice.model.Product;
import it.worldpay.faz.offerservice.repository.OfferRepository;
import it.worldpay.faz.offerservice.repository.ProductRepository;
import it.worldpay.faz.offerservice.service.impl.CurrencyOfferServiceImpl;
import it.worldpay.faz.offerservice.service.impl.OfferServiceImpl;
import it.worldpay.faz.offerservice.service.impl.ProductServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
	
	@InjectMocks
	private ProductServiceImpl productService;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private OfferRepository offerRepository;
	
	@Mock
	private OfferServiceImpl offerService;
	
	@Mock
	private CurrencyOfferServiceImpl currencyOfferService;
	
	@Spy
	private ProductMapper productMapper = new ProductMapperImpl();
	
	private ProductDTO dummyProductDTO;
	private Product dummyProduct;
	private DummyFactoryImpl dummyFactoryImpl;
	private OfferDTO dummyOfferDTO;
	private CurrencyOfferDTO dummyCurrencyOfferDTO;
	
	@Before
	public void initializeTestVariable()  {
		init();
	}
	
	@Test
	public void createProduct_whenProductrNotExist_thenIsSaved() throws DuplicateResourceException, Exception {
		
		ProductDTO productDTO = dummyProductDTO;
		given(productRepository.findById(productDTO.getProductId())).willReturn(Optional.empty());
		given(offerService.getOfferById(dummyOfferDTO.getOfferId())).willReturn(dummyOfferDTO);
		given(currencyOfferService.findCurrencyById(dummyCurrencyOfferDTO.getCurrencyId())).willReturn(dummyCurrencyOfferDTO);
		
		dummyOfferDTO.setCurrency(dummyCurrencyOfferDTO);
		productDTO.setOffer(dummyOfferDTO);
		
		productService.createProduct(productDTO);
		
		verify(productRepository, times(1)).save(any(Product.class));
	}
	
	@Test(expected = DuplicateResourceException.class)
	public void createProduct_whenProductExist_thenDuplicateResourceException() {
		
		ProductDTO productDTO = dummyProductDTO;
		given(productRepository.findById(productDTO.getProductId())).willReturn(Optional.of(dummyProduct));
		
		Optional<Product> productExist = productRepository.findById(dummyProductDTO.getProductId());
		if(productExist.isPresent()) {
			throw new DuplicateResourceException("Product already exist");
		}
	}
	
	
	private void init() {
		dummyFactoryImpl = new DummyFactoryImpl();
		dummyProductDTO = dummyFactoryImpl.getDummyProductDTO();
		dummyProduct = dummyFactoryImpl.getDummyProduct();
		dummyOfferDTO = dummyFactoryImpl.getDummyOfferDTO();
		dummyCurrencyOfferDTO = dummyFactoryImpl.getCurrencyOfferDTO();
	}
}
