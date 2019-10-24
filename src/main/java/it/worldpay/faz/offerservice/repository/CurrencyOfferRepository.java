package it.worldpay.faz.offerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.worldpay.faz.offerservice.model.CurrencyOffer;

public interface CurrencyOfferRepository extends JpaRepository<CurrencyOffer, String> {
	
	@Query(value = "SELECT * FROM CURRENCY WHERE CURRENCY_ID = ?1", nativeQuery = true)
	public CurrencyOffer findByUUID(String uuid);

}
