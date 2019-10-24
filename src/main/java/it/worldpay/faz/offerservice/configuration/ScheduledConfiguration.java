package it.worldpay.faz.offerservice.configuration;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import it.worldpay.faz.offerservice.service.CurrencyOfferService;

@Configuration
@EnableScheduling
public class ScheduledConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledConfiguration.class);
	
	@Autowired
	private CurrencyOfferService currencyOfferService;
	
   @Scheduled(fixedRate = 30000)
    public void scheduledUpdateTask() {
    	log.info(Thread.currentThread().getName() + " The currency scheduledUpdateTask executed at "+ new Date());
    	
        currencyOfferService.currencyScheduledUpdate();
    }
}
