package it.worldpay.faz.offerservice.configuration;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import it.worldpay.faz.offerservice.service.CurrencyOfferService;
import it.worldpay.faz.offerservice.service.OfferService;

@Configuration
@EnableScheduling
public class ScheduledConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledConfiguration.class);
	
	@Autowired
	private CurrencyOfferService currencyOfferService;
	
	@Autowired
    private OfferService offerService;

	@Scheduled(fixedRate = 300000)//(cron = "0 0/25 0,1 * * *")  should be implemented this in real situation
	public void scheduledUpdateTask() {
		log.info(Thread.currentThread().getName() + " The currency scheduledUpdateTask executed at "+ new Date());

		currencyOfferService.currencyScheduledUpdate();
	}
	@Scheduled(fixedRate = 360000)//(cron = "0 0/30 0,1 * * *") should be implemented this in real situation
	public void scheduledExpireOfferUpdateTask() {
		log.info(Thread.currentThread().getName() + " The currency offerScheduledUpdateExpire executed at "+ new Date());

		offerService.offerScheduledUpdateExpire();
	}
}
