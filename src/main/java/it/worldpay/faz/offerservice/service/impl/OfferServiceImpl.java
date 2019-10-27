package it.worldpay.faz.offerservice.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.worldpay.faz.offerservice.dto.OfferDTO;
import it.worldpay.faz.offerservice.dto.ProductDTO;
import it.worldpay.faz.offerservice.dto.mapper.OfferMapper;
import it.worldpay.faz.offerservice.exception.DuplicateResourceException;
import it.worldpay.faz.offerservice.exception.OfferDatesException;
import it.worldpay.faz.offerservice.exception.OfferExpiredException;
import it.worldpay.faz.offerservice.exception.ResourceNotFoundException;
import it.worldpay.faz.offerservice.exception.ServerNotAvailableException;
import it.worldpay.faz.offerservice.model.Offer;
import it.worldpay.faz.offerservice.repository.OfferRepository;
import it.worldpay.faz.offerservice.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService {
	
	private static final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private OfferMapper offerMapper;

	@Override
	public List<OfferDTO> getAllOffers() throws ResourceNotFoundException {
		log.info("getAllOffersDTO()");
		
		List<OfferDTO> listOfferDTO = offerMapper.mapListFromModelToDTO(offerRepository.findAll().stream()
												.collect(Collectors.toList()));
		
		for (OfferDTO offerDTO : listOfferDTO) {
			autoExipire(offerDTO, offerRepository);
		}
		
		if(listOfferDTO.size() == 0) {
			throw new ResourceNotFoundException("List is empty");
		}
		
		return listOfferDTO;
	}
	
	@Override
	public OfferDTO getOfferById(String offerId) throws ResourceNotFoundException {
		log.info("getOfferDTO() {} = ", offerId);
		
		Optional<Offer> offer = Optional.ofNullable(offerRepository.findByUUID(offerId));
		
		if(!offer.isPresent()) {
			throw new ResourceNotFoundException("Offer not found");
		}
		
		OfferDTO offerDTO = offerMapper.fromModelToDTO(offer.get());
		autoExipire(offerDTO, offerRepository);
		
		log.info("getOfferDTO() {} = ", offerDTO.toString());
		return offerDTO;
	}

	@Override
	@Transactional
	public void createOffer(OfferDTO offerDTO) throws DuplicateResourceException, OfferDatesException {
		log.info("createOffer() {} = ", offerDTO.toString());
		
		/*
		 * The id generetion is been implemented using UUID.randomUUID().toString()
		 * to ensure uniqueness and to avoid the hard coding into jsons, during tests, of fake id 
		 * not genereted by the back-end. 
		 * This choise is due to the use of h2 embedded db that doesn't allow both Hibernate 
		 * strategies like AUTO or IDENTITY and properties mapping to manage pk fk reletions.
		 * This well documented problem is easily avoided by using a fully fledge RDBMS.
		 */
		if (offerDTO.getOfferId().equals("")) {
			String uuid = UUID.randomUUID().toString();
			offerDTO.setOfferId(uuid);
		}
		
		Optional<Offer> offerExist = Optional.ofNullable(offerRepository.findByUUID(offerDTO.getOfferId()));
		if(offerExist.isPresent()) {
			throw new DuplicateResourceException("Offer already exist");
		}
		
		checkOfferDates(offerDTO);
		checkTodayisAfterEnd(offerDTO);
		
		List<ProductDTO> listProductTOfferDTO = offerDTO.getProductList().stream()
				.map(p -> new ProductDTO(
						p.getProductId(),
						p.getProductName(),
						p.getProductDescription(),
						p.getProductPrice(),
						new OfferDTO(offerDTO.getOfferId()),
						p.getIsActive(),
						p.getProductDiscountedPrice()))
				.collect(Collectors.toList());
		
		offerDTO.setProductList(listProductTOfferDTO);
		
		Offer offer = offerMapper.toModelFromDTO(offerDTO);
		log.info("createOffer() {} = ", offerDTO.toString());
		offerRepository.save(offer);
	}

	@Override
	@Transactional
	public OfferDTO updateOffer(OfferDTO offerDTO) throws OfferDatesException, OfferExpiredException{
		log.info("updateOffer() {} = ", offerDTO.toString());
		
		checkIfOfferExpired(offerDTO, offerRepository);
		checkOfferDates(offerDTO);
		autoExipire(offerDTO, offerRepository);
		
		Offer offer = offerMapper.toModelFromDTO(offerDTO);
		offerDTO = offerMapper.fromModelToDTO(offerRepository.save(offer));
		
		return offerDTO;
	}

	@Override
	@Transactional
	public void deleteOffer(OfferDTO offerDTO) throws OfferDatesException, OfferExpiredException {
		log.info("deleteOffer() {} = ", offerDTO.toString());
		
		checkOfferDates(offerDTO);
		checkIfOfferExpired(offerDTO, offerRepository);
		
		Offer offer = offerMapper.toModelFromDTO(offerDTO);
		offer.setExpired(true);
		offer.setOfferExpiringDate(new Date());
		
		offerRepository.save(offer);
	}
	
	@Override
    @Transactional
    public void offerScheduledUpdateExpire() throws ServerNotAvailableException {
        log.info("offerScheduledUpdateExpire()");
        
        List<OfferDTO> listMustExpireOffers = offerMapper.mapListFromModelToDTO(offerRepository.findAllMustExpireOffers());
        log.info("listMustExpireOffers.size() = {}", listMustExpireOffers.size());
        
        for (OfferDTO offerDTO : listMustExpireOffers) {
            autoExipire(offerDTO, offerRepository);
        }
        
    }

	private void checkIfOfferExpired(OfferDTO offerDTO, OfferRepository offerRepository
			) throws OfferExpiredException {
		log.info("checkIfOfferExpired()");
		
		Boolean isExipireFromDB = offerRepository.findByUUID(offerDTO.getOfferId()).isExpired();
		
		if(isExipireFromDB) {
			throw new OfferExpiredException("Offer is already exipred");
		}
	}
	
	private OfferDTO autoExipire(OfferDTO offerDTO, OfferRepository offerRepository) {
		log.info("autoExipire()");
		
		LocalDate endOfferDate = offerDTO.getOfferExpiringDate()
									.toInstant()
									.atZone(ZoneId.systemDefault())
									.toLocalDate();
		LocalDate today = LocalDate.now();
		
		if(today.isAfter(endOfferDate)) {
			offerDTO.setExpired(true);
			offerRepository.save(offerMapper.toModelFromDTO(offerDTO));
		}
		
		return offerDTO;
	}
	
	private void checkOfferDates(OfferDTO offerDTO) throws OfferDatesException {
		log.info("checkOfferDates()");
		
		LocalDate startOfferDate = offerDTO.getOfferStartingDate()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		
		LocalDate endOfferDate = offerDTO.getOfferExpiringDate()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		
		if(endOfferDate.isBefore(startOfferDate)) {
			throw new OfferDatesException("Expiring date must be after starting date");
		}
		
	}
	
	private void checkTodayisAfterEnd(OfferDTO offerDTO) throws OfferDatesException {
		log.info("checkTodayisAfterEnd() {offerDTO}");
		
		LocalDate endOfferDate = offerDTO.getOfferExpiringDate()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		
		LocalDate today = LocalDate.now();
		
		if(today.isAfter(endOfferDate)) {
			throw new OfferDatesException("Attention the offer dates result already expired");
		}
	}

}
