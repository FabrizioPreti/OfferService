package it.worldpay.faz.offerservice.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import it.worldpay.faz.offerservice.dummy.DummyFactoryImpl;
import it.worldpay.faz.offerservice.exception.ResourceNotFoundException;
import it.worldpay.faz.offerservice.model.CurrencyOffer;
import it.worldpay.faz.offerservice.model.Offer;
import it.worldpay.faz.offerservice.model.Product;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
	
	private Product dummyProduct;
	private Offer dummyOffer;
	
	@Autowired
    private TestEntityManager testEntityManager;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Before
	public void initializeTestVariable() {
		init();
	}

	@Test
	public void whenProductisSavedItCanBeRetrieved() {
		
		//if not saved the transient property the association break
		testEntityManager.persist(dummyProduct.getOffer());
		testEntityManager.flush();
		
		testEntityManager.persist(dummyProduct);
		testEntityManager.flush();
		
		Optional<Product> product = productRepository.findById(dummyProduct.getProductId());
			
		assertTrue(product.isPresent());
		assertEquals(dummyProduct.getProductId(), product.get().getProductId());
	}
	
	@Test
	public void whenProductIsSavedFindByUUIDCanRetrieve() {
		
		//if not saved the transient property the association break
		testEntityManager.persist(dummyProduct.getOffer());
		testEntityManager.flush();
		
		testEntityManager.persist(dummyProduct);
		testEntityManager.flush();
		
		
		Optional<Product> product = 
				Optional.ofNullable(productRepository.findByUUID(dummyProduct.getProductId()));
		
		assertTrue(product.isPresent());
		assertEquals(dummyProduct.getProductId(), product.get().getProductId());
	}
	
	@Test
	public void whenProductIdNotExistResourceNotFoundException() {
		
		Optional<Product> product =  
				Optional.ofNullable(productRepository.findByUUID("not-existing-id"));
		
		assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			Product productProd = product.orElseThrow(ResourceNotFoundException::new);
		});
	}
	
	@Test
	public void whenProductListIsSavedItCanBeRetrieved() {
		
		getAllProducts();
		List<Product> listProducts = productRepository.findAll();
		
		assertTrue(listProducts.size() > 0);
	}
	
	private void init() {
		DummyFactoryImpl dummyFactoryImpl = new DummyFactoryImpl();
		dummyProduct = dummyFactoryImpl.getDummyProduct();
		dummyOffer = dummyFactoryImpl.getDummyOffer();
	}
	
	//if not saved the transient property the association break
	private void getAllProducts(){
		
		List<Product> list = new ArrayList<>();
		
		CurrencyOffer currency1 = new CurrencyOffer("1");
		CurrencyOffer currency2 = new CurrencyOffer("2");
		
		testEntityManager.persist(currency1);
		testEntityManager.persist(currency2);
		testEntityManager.flush();
		
		Offer offer1 = dummyOffer;
		offer1.setOfferId("1-O");
		offer1.setCurrency(currency1);
		
		Offer offer2 = dummyOffer;
		offer2.setOfferId("2-O");
		offer2.setCurrency(currency2);
		
		testEntityManager.persist(offer1);
		testEntityManager.persist(offer2);
		testEntityManager.flush();
		
		Product product1 = dummyProduct;
		product1.setOffer(offer1);
		product1.setProductId("1-0");
		
		Product product2 = dummyProduct;
		product2.setOffer(offer2);
		product2.setProductId("2-0");
		
		testEntityManager.persist(product1);
		testEntityManager.persist(product2);
		testEntityManager.flush();
		
		list.add(product1);
		list.add(product2);
	}
}
