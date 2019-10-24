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

import it.worldpay.faz.offerservice.dto.ProductDTO;
import it.worldpay.faz.offerservice.dummy.DummyFactoryImpl;
import it.worldpay.faz.offerservice.service.ProductService;
import it.worldpay.faz.offerservice.utility.Paths;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
	
	@Before
	public void initializeTestVariable() {
		init();
	}
	
	private ProductDTO dummyProductDTO;
	private List<ProductDTO> dummyListProductDTO;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ProductService productService;
	
	@Test
	public void get_assetTrue_whenallOfferIsValid200() throws Exception{
		
		given(productService.getAllProducts()).willReturn(dummyListProductDTO);
		mockMvc.perform(
				get(Paths.V1_PRODUCTS) 
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void get_whenOfferIsValid_thenResponseIs200()throws Exception{
		
		given(productService.getProductById(anyString())).willReturn(dummyProductDTO);
		mockMvc.perform(
				get((Paths.V1_PRODUCTS_ID), dummyProductDTO.getProductId()) 
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void post_whenOfferIsValid_thenResponseIs201()throws Exception{
		
		doNothing().when(productService).createProduct(dummyProductDTO);
		
		mockMvc.perform(
				post(Paths.V1_PRODUCTS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(dummyProductDTO)))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void update_whenOfferIsValid_thenResponseIs200()throws Exception{
		ProductDTO updatedDummyProductDTO = dummyProductDTO;
		updatedDummyProductDTO.setProductDescription("WoW");
		
		doReturn(updatedDummyProductDTO).when(productService).updateProduct(dummyProductDTO);
		
		mockMvc.perform(
				put(Paths.V1_PRODUCTS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(updatedDummyProductDTO)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void delete_whenOfferIsValid_thenResponseIs204()throws Exception{
		ProductDTO deletedDummyProductDTO = dummyProductDTO;
		deletedDummyProductDTO.setIsActive(false);
		
		doNothing().when(productService).deleteProduct(dummyProductDTO);
		mockMvc.perform(
				get((Paths.V1_PRODUCTS_ID), deletedDummyProductDTO.getProductId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(dummyProductDTO)))
				.andExpect(status().isOk());
	}
	
	
	
	private void init() {
		DummyFactoryImpl dummyFactoryDTOImpl = new DummyFactoryImpl();
		dummyProductDTO = dummyFactoryDTOImpl.getDummyProductDTO();
		dummyListProductDTO = dummyFactoryDTOImpl.getAllDummyProductDTO();
	}
}
