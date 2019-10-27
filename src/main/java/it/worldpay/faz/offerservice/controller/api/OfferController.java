package it.worldpay.faz.offerservice.controller.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.service.OfferService;
import it.worldpay.faz.offerservice.utility.Paths;


@CrossOrigin(origins="*")
@RestController
public class OfferController {
	
	private static final Logger log = LoggerFactory.getLogger(OfferController.class);
	
	@Autowired
	private OfferService offerService;
	
	
	@GetMapping(path = Paths.V1_OFFERS)
	public ResponseEntity<List<OfferDTO>> getAllOffers() throws Exception{
		
		List<OfferDTO> listOfferDTO = offerService.getAllOffers();
		
		log.info("getAllOffers() {}", listOfferDTO.size());
		return ResponseEntity.ok(listOfferDTO);
	}
	
	@GetMapping(path = Paths.V1_OFFERS_ID)
	public ResponseEntity<OfferDTO> getOffer(@PathVariable("id") String offerId) throws Exception{
		
		OfferDTO offerDTO = offerService.getOfferById(offerId);
		
		log.info("getOffer() {}", offerDTO.toString());
		return ResponseEntity.ok(offerDTO);
	}
	
	@PostMapping(path = Paths.V1_OFFERS)
	public ResponseEntity<HttpStatus> createOffer(@RequestBody OfferDTO offerDTO) throws Exception{
		
		offerService.createOffer(offerDTO);
		
		log.info("createOffer() {}", offerDTO.toString());
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	@PutMapping(path = Paths.V1_OFFERS)
	public ResponseEntity<OfferDTO> updateOffer(@RequestBody OfferDTO offerDTO) throws Exception{
		
		OfferDTO offer = offerService.updateOffer(offerDTO);
		
		log.info("updateOffer() {}", offerDTO.toString());
		return ResponseEntity.ok(offer);
	}
	
	@PutMapping(path = Paths.V1_OFFERS_DELETE)
	public ResponseEntity<HttpStatus> deleteOffer(@RequestBody OfferDTO offerDTO) throws Exception{
		
		offerService.deleteOffer(offerDTO);
		
		log.info("deleteOffer() {}", offerDTO.toString());
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
}
