package it.worldpay.faz.offerservice.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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


@RunWith(SpringRunner.class)
@DataJpaTest
public class CurrencyRepositoryTest {
	
	public CurrencyOffer dummyCurrencyOffer;
	
	@Autowired
	private CurrencyOfferRepository currencyOfferRepository;
	
	@Autowired
    private TestEntityManager testEntityManager;
	
	@Before
	public void initializeTestVariable() {
		init();
	}
	
	@Test
	public void whenCurrencyisSavedItCanBeRetrieved() {
		testEntityManager.persist(dummyCurrencyOffer);
		testEntityManager.flush();
		
		Optional<CurrencyOffer> currency = currencyOfferRepository.findById(dummyCurrencyOffer.getCurrencyId());
			
		assertTrue(currency.isPresent());
		assertEquals(dummyCurrencyOffer.getCurrencyId(), currency.get().getCurrencyId());
	}
	
	@Test
	public void whenCurrencyIsSavedFindByUUIDCanRetrieve() {
		
		testEntityManager.persist(dummyCurrencyOffer);
		testEntityManager.flush();
		
		
		Optional<CurrencyOffer> currency = 
				Optional.ofNullable(currencyOfferRepository.findByUUID(dummyCurrencyOffer.getCurrencyId()));
		
		assertTrue(currency.isPresent());
		assertEquals(dummyCurrencyOffer.getCurrencyId(), currency.get().getCurrencyId());
	}
	
	@Test
	public void whenCurrencyIdNotExistResourceNotFoundException() {
		
		Optional<CurrencyOffer> currency = 
				Optional.ofNullable(currencyOfferRepository.findByUUID("not-existing-id"));
		
		assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			CurrencyOffer curr = currency.orElseThrow(ResourceNotFoundException::new);
		});
	}
	
	private void init() {
		DummyFactoryImpl dummyFactoryDTOImpl = new DummyFactoryImpl();
		dummyCurrencyOffer = dummyFactoryDTOImpl.getCurrencyOffer();
	}
	
}
