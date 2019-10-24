package it.worldpay.faz.offerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.worldpay.faz.offerservice.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, String>{
	
	
	@Query(value = "SELECT * FROM OFFER WHERE OFFER_ID = ?1", nativeQuery = true)
	public Offer findByUUID(String uuid);
	
}
