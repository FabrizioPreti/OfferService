package it.worldpay.faz.offerservice.controller.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import it.worldpay.faz.offerservice.dto.BaseCurrencyDTO;
import it.worldpay.faz.offerservice.dummy.DummyFactoryImpl;
import it.worldpay.faz.offerservice.service.CurrencyOfferService;
import it.worldpay.faz.offerservice.utility.Paths;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CurrencyOfferController.class)
public class CurrencyOfferControllerTest {
	
	@Before
	public void initializeTestVariable() {
		init();
	}
	
	private BaseCurrencyDTO dummyAllCurrencyOfferDTO;

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CurrencyOfferService currencyOfferService;
	
	@Test
	public void get_assetTrue_whenallOfferIsValid200() throws Exception{
		
		given(currencyOfferService.getAllCurrencyFromAPI()).willReturn(dummyAllCurrencyOfferDTO);
		mockMvc.perform(
				get(Paths.V1_CURRENCIES) 
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	private void init() {
		DummyFactoryImpl dummyFactoryDTOImpl = new DummyFactoryImpl();
		dummyAllCurrencyOfferDTO = dummyFactoryDTOImpl.getAllDummyBaseCurrencyDTO();
	}

}
