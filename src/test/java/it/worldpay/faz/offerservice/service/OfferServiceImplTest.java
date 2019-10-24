package it.worldpay.faz.offerservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.vavr.Tuple2;
import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.dummy.DummyFactoryImpl;
import it.worldpay.faz.offerservice.exception.DuplicateResourceException;
import it.worldpay.faz.offerservice.exception.OfferExpiredException;
import it.worldpay.faz.offerservice.model.Offer;
import it.worldpay.faz.offerservice.repository.OfferRepository;
import it.worldpay.faz.offerservice.service.impl.OfferServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferServiceImplTest {
	
	@InjectMocks
	private OfferServiceImpl offerService;
	
	@Mock
	private OfferRepository offerRepository;
	
	private OfferDTO dummyOfferDTO;
	private Offer dummyOffer;
	private DummyFactoryImpl dummyFactoryImpl;
	
	@Before
	public void initializeTestVariable()  {
		init();
	}
	
	@Test
	public void createOffer_whenOfferNotExist_thenIsSaved(){
		
		given(offerRepository.findById(dummyOfferDTO.getOfferId())).willReturn(Optional.empty());
		
		offerService.createOffer(dummyOfferDTO);
		
		verify(offerRepository, times(1)).save(any(Offer.class));
	}
	
	@Test(expected = DuplicateResourceException.class)
	public void createOffer_whenOfferExist_thenDuplicateResourceException() {
		
		OfferDTO offerDTO = dummyOfferDTO;
		given(offerRepository.findById(offerDTO.getOfferId())).willReturn(Optional.of(dummyOffer));
		
		Optional<Offer> offerExist = offerRepository.findById(dummyOfferDTO.getOfferId());
		if(offerExist.isPresent()) {
			throw new DuplicateResourceException("Offer already exist");
		}
	}
	
	@Test(expected = OfferExpiredException.class)
	public void autoExpire_whenOfferExistAndIsExpired_thenNoUpdateIsPossible() {
		
		Tuple2<Date, Date> tupla2Dates = 
		dummyFactoryImpl.getExpiringAndStartingDates("2019-10-20 11:00:00", "2019-10-10 11:00:00");
		
		Date offerExpiringDate = tupla2Dates._1;
		Date offerStartingDate = tupla2Dates._2;
		
		OfferDTO offerDTO = dummyOfferDTO;
		offerDTO.setOfferExpiringDate(offerExpiringDate);
		offerDTO.setOfferStartingDate(offerStartingDate);
		
		LocalDate endOfferDate = offerDTO.getOfferExpiringDate()
						.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();
		LocalDate today = LocalDate.now();
		
		if(today.isAfter(endOfferDate)) {
			throw new OfferExpiredException();
		}
	}
	
	@Test(expected = OfferExpiredException.class)
	public void checkIfOfferExpiredwhenOfferExistAndIsExpired_thenNoUpdateIsPossible() {
		dummyOffer.setExpired(true);
		OfferDTO offerDTO = dummyOfferDTO;
		given(offerRepository.findById(offerDTO.getOfferId())).willReturn(Optional.of(dummyOffer));
		
		Boolean isExipireFromDB = offerRepository.findById(offerDTO.getOfferId()).get().isExpired();
		
		if(isExipireFromDB) {
			throw new OfferExpiredException("Offer is already exipred");
		}
	}
	
	
	private void init() {
		dummyFactoryImpl = new DummyFactoryImpl();
		dummyOfferDTO = dummyFactoryImpl.getDummyOfferDTO();
		dummyOffer = dummyFactoryImpl.getDummyOffer();
	}
}
