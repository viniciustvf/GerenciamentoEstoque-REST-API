package com.trier.gerenciamentoestoque.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Seller;


@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer>{
	
	List<Seller> findByNameStartingWithIgnoreCaseOrderByNameDesc(String name);
	
	Optional<Seller> findByRegistration(String registration);
	
}