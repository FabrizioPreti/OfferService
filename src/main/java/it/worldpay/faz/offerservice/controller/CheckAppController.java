package it.worldpay.faz.offerservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.worldpay.faz.offerservice.utility.Paths;

@CrossOrigin(origins = "*")
@RestController
public class CheckAppController {
	
	private static final String RESPONSE_MESSAGE="The service is Up and Running!";

	@GetMapping(Paths.CHECK)
	public ResponseEntity<String> check( )  {
		return ResponseEntity.ok(RESPONSE_MESSAGE);
	}

}
