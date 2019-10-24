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

@RunWith(SpringRunner.class)
@DataJpaTest
public class OfferRepositoryTest {
	
	private Offer dummyOffer;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
    private TestEntityManager testEntityManager;
	
	@Before
	public void initializeTestVariable() {
		init();
	}
	
	@Test
	public void whenOfferisSavedItCanBeRetrieved() {
		
		//if not saved the transient property the association break
		testEntityManager.persist(dummyOffer.getCurrency());
		testEntityManager.flush();
		
		testEntityManager.persist(dummyOffer);
		testEntityManager.flush();
		
		Optional<Offer> offer = offerRepository.findById(dummyOffer.getOfferId());
			
		assertTrue(offer.isPresent());
		assertEquals(dummyOffer.getOfferId(), offer.get().getOfferId());
	}
	
	@Test
	public void whenOfferIsSavedFindByUUIDCanRetrieve() {
		
		//if not saved the transient property the association break
		testEntityManager.persist(dummyOffer.getCurrency());
		testEntityManager.flush();
		
		testEntityManager.persist(dummyOffer);
		testEntityManager.flush();
		
		
		Optional<Offer> offer = 
				Optional.ofNullable(offerRepository.findByUUID(dummyOffer.getOfferId()));
		
		assertTrue(offer.isPresent());
		assertEquals(dummyOffer.getOfferId(), offer.get().getOfferId());
	}
	
	@Test
	public void whenOfferIdNotExistResourceNotFoundException() {
		
		Optional<Offer> offer = 
				Optional.ofNullable(offerRepository.findByUUID("not-existing-id"));
		
		assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			Offer offerMo = offer.orElseThrow(ResourceNotFoundException::new);
		});
	}
	
	@Test
	public void whenOfferListIsSavedItCanBeRetrieved() {
		
		getAllOffers();
		List<Offer> listOffer = offerRepository.findAll();
		
		assertTrue(listOffer.size() > 0);
	}
	

	
	private void init() {
		DummyFactoryImpl dummyFactoryImpl = new DummyFactoryImpl();
		dummyOffer = dummyFactoryImpl.getDummyOffer();
	}
	
	//if not saved the transient property the association break
	private void getAllOffers(){
		
		List<Offer> list = new ArrayList<>();
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
		
		list.add(offer1);
		list.add(offer2);
	}

}
