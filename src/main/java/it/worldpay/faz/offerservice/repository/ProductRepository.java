package it.worldpay.faz.offerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.worldpay.faz.offerservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
	
	@Query(value = "SELECT * FROM PRODUCT WHERE PRODUCT_ID = ?1", nativeQuery = true)
	public Product findByUUID(String uuid);
}
