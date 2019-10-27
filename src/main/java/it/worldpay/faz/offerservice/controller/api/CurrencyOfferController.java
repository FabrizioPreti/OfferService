package it.worldpay.faz.offerservice.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.worldpay.faz.offerservice.dto.BaseCurrencyDTO;
import it.worldpay.faz.offerservice.exception.ServerNotAvailableException;
import it.worldpay.faz.offerservice.service.CurrencyOfferService;
import it.worldpay.faz.offerservice.utility.Paths;

@CrossOrigin(origins="*")
@RestController
public class CurrencyOfferController {
	
	private static final Logger log = LoggerFactory.getLogger(CurrencyOfferController.class);
	
	@Autowired
	private CurrencyOfferService currencyOfferService;
	
	@GetMapping(path = Paths.V1_CURRENCIES)
	public ResponseEntity<BaseCurrencyDTO> getAllCurrency() throws ServerNotAvailableException{
		
		BaseCurrencyDTO allCurrency = currencyOfferService.getAllCurrencyFromAPI();

		log.info("getAllCurrency().size() {}", allCurrency.toString());
		return ResponseEntity.ok(allCurrency);		
	}

}
