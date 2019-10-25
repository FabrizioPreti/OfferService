package it.worldpay.faz.offerservice.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import it.worldpay.faz.offerservice.dto.BaseCurrencyDTO;
import it.worldpay.faz.offerservice.dto.CurrencyOfferDTO;
import it.worldpay.faz.offerservice.dto.mapper.CurrencyOfferMapper;
import it.worldpay.faz.offerservice.exception.ResourceNotFoundException;
import it.worldpay.faz.offerservice.exception.ServerNotAvailableException;
import it.worldpay.faz.offerservice.model.CurrencyOffer;
import it.worldpay.faz.offerservice.model.Offer;
import it.worldpay.faz.offerservice.repository.CurrencyOfferRepository;
import it.worldpay.faz.offerservice.service.CurrencyOfferService;
import it.worldpay.faz.offerservice.utility.CurrencyEnum;
import it.worldpay.faz.offerservice.utility.UtilConstants;

@Service
public class CurrencyOfferServiceImpl implements CurrencyOfferService {
	
	private static final Logger log = LoggerFactory.getLogger(CurrencyOfferServiceImpl.class);
	
	@Autowired
	private CurrencyOfferRepository currencyOfferRepository;


	@Override
	public BaseCurrencyDTO getAllCurrencyFromAPI() throws ServerNotAvailableException{
		log.info("getAllCurrencyFromAPI() consuming external API");

		BaseCurrencyDTO baseCurrency = consumeRestApi();
		return baseCurrency;
	}
	
	@Override
	public CurrencyOfferDTO findCurrencyById(String uuid) throws ResourceNotFoundException {
		String id = uuid != null && !uuid.equals("") ? uuid : "id null";
		log.info("findCurrencyById() {String uuid} = " + id);
			
		Optional<CurrencyOfferDTO> currencyOfferDTO = 
				Optional.ofNullable(CurrencyOfferMapper.fromModelToDTO(currencyOfferRepository.findByUUID(uuid)));
		
		if(!currencyOfferDTO.isPresent()) {
			throw new ResourceNotFoundException("Currency not found");
		}
		log.info("currencyDTO.toString = " + currencyOfferDTO.toString());
		return currencyOfferDTO.get();
	}
	
	@Override
	@Transactional
	public void currencyScheduledUpdate() throws ServerNotAvailableException {
		log.info("currencySheduledUpdate()");
		
		//this method return the updated currency rates from external API
		BaseCurrencyDTO baseCurrency = consumeRestApi();
		
		Offer offer = new Offer(UtilConstants.DEFAULT_OFFER_ID);
		List<Offer> offerList = new ArrayList<>();
		offerList.add(offer);
		List<CurrencyOffer> listCurrencyOffer = new ArrayList<>();
		
		CurrencyOffer currencyOfferEUR = new CurrencyOffer();
		currencyOfferEUR.setCurrencyId(UtilConstants.CURRENCY_EUR_ID);
		currencyOfferEUR.setCurrencyDescription(CurrencyEnum.EUR.toString());
		currencyOfferEUR.setCurrencyRate(new BigDecimal(baseCurrency.getRates().getEUR()));
		
		CurrencyOffer currencyOfferUSD = new CurrencyOffer();
		currencyOfferUSD.setCurrencyId(UtilConstants.CURRENCY_USD_ID);
		currencyOfferUSD.setCurrencyDescription(CurrencyEnum.USD.toString());
		currencyOfferUSD.setCurrencyRate(new BigDecimal(baseCurrency.getRates().getUSD()));
		
		CurrencyOffer currencyOfferCAD = new CurrencyOffer();
		currencyOfferCAD.setCurrencyId(UtilConstants.CURRENCY_CAD_ID);
		currencyOfferCAD.setCurrencyDescription(CurrencyEnum.CAD.toString());
		currencyOfferCAD.setCurrencyRate(new BigDecimal(baseCurrency.getRates().getCAD()));
		
		listCurrencyOffer.add(currencyOfferEUR);
		listCurrencyOffer.add(currencyOfferUSD);
		listCurrencyOffer.add(currencyOfferUSD);
		
		currencyOfferRepository.saveAll(listCurrencyOffer);
	}
	
	private BaseCurrencyDTO consumeRestApi()  throws ServerNotAvailableException{
		log.info("consumeRestApi()");
		
		String currencyJsonString = new RestTemplate().getForObject(UtilConstants.URL, String.class);
		
		Gson gson = new Gson();
		BaseCurrencyDTO baseCurrency = gson.fromJson(currencyJsonString, BaseCurrencyDTO.class);
		return baseCurrency;
	}

}
