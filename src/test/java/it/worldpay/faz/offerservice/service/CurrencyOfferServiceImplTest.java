package it.worldpay.faz.offerservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.worldpay.faz.offerservice.dto.CurrencyOfferDTO;
import it.worldpay.faz.offerservice.dummy.DummyFactoryImpl;
import it.worldpay.faz.offerservice.model.CurrencyOffer;
import it.worldpay.faz.offerservice.repository.CurrencyOfferRepository;
import it.worldpay.faz.offerservice.service.impl.CurrencyOfferServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyOfferServiceImplTest {

	@InjectMocks
	private CurrencyOfferServiceImpl currencyOfferService;
	
	@Mock
	private CurrencyOfferRepository currencyOfferRepository;
	
	private DummyFactoryImpl dummyFactoryImpl;
	private CurrencyOfferDTO dummyCurrencyOfferDTO;
	private CurrencyOffer dummyCurrencyOffer;
	
	@Before
	public void initializeTestVariable()  {
		init();
	}
	
	@Test
	public void getCurrency_when_currencyIsSaved_then_currencyValueAndDescriptionAreAvailable() {
		
		String currencyDescription = "EUR";
		
		CurrencyOfferDTO currencyOfferDTO = dummyCurrencyOfferDTO;
		given(currencyOfferRepository.findById(currencyOfferDTO.getCurrencyId())).willReturn(Optional.of(dummyCurrencyOffer));
		
		assertEquals(currencyDescription, dummyCurrencyOffer.getCurrencyDescription());
	}
	
	private void init() {
		dummyFactoryImpl = new DummyFactoryImpl();
		dummyCurrencyOfferDTO = dummyFactoryImpl.getCurrencyOfferDTO();
		dummyCurrencyOffer = dummyFactoryImpl.getCurrencyOffer();
	}
}
