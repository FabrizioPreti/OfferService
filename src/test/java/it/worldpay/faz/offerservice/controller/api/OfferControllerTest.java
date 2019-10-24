package it.worldpay.faz.offerservice.controller.api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.dummy.DummyFactoryImpl;
import it.worldpay.faz.offerservice.service.OfferService;
import it.worldpay.faz.offerservice.utility.Paths;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = OfferController.class)
public class OfferControllerTest {
	
	@Before
	public void initializeTestVariable() {
		init();
	}

	private OfferDTO dummyOfferDTO;
	private List<OfferDTO> dummyListOfferDTO;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private OfferService offerService;
	
	@Test
	public void get_assetTrue_whenallOfferIsValid200() throws Exception{
		
		given(offerService.getAllOffers()).willReturn(dummyListOfferDTO);
		mockMvc.perform(
				get(Paths.V1_OFFERS) 
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void get_whenOfferIsValid_thenResponseIs200()throws Exception{
		
		given(offerService.getOfferById(anyString())).willReturn(dummyOfferDTO);
		mockMvc.perform(
				get((Paths.V1_OFFERS_ID), dummyOfferDTO.getOfferId()) 
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void post_whenOfferIsValid_thenResponseIs201()throws Exception{
		
		doNothing().when(offerService).createOffer(dummyOfferDTO);
		
		mockMvc.perform(
				post(Paths.V1_OFFERS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(dummyOfferDTO)))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void update_whenOfferIsValid_thenResponseIs200()throws Exception{
		OfferDTO updatedDummyOfferDTO = dummyOfferDTO;
		updatedDummyOfferDTO.setOfferDescription("WoW");
		
		doReturn(updatedDummyOfferDTO).when(offerService).updateOffer(dummyOfferDTO);
		
		mockMvc.perform(
				put(Paths.V1_OFFERS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(updatedDummyOfferDTO)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void delete_whenOfferIsValid_thenResponseIs204()throws Exception{
		OfferDTO deletedDummyOfferDTO = dummyOfferDTO;
		deletedDummyOfferDTO.setExpired(true);
		
		doNothing().when(offerService).deleteOffer(dummyOfferDTO);
		mockMvc.perform(
				get((Paths.V1_OFFERS_ID), deletedDummyOfferDTO.getOfferId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(dummyOfferDTO)))
				.andExpect(status().isOk());
	}
	
	
	
	private void init() {
		DummyFactoryImpl dummyFactoryDTOImpl = new DummyFactoryImpl();
		dummyOfferDTO =  dummyFactoryDTOImpl.getDummyOfferDTO();
		dummyListOfferDTO = dummyFactoryDTOImpl.getAllDummyOfferDTO();
	}
}
