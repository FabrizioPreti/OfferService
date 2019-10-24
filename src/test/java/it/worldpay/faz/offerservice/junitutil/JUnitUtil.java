package it.worldpay.faz.offerservice.junitutil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JUnitUtil {
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
