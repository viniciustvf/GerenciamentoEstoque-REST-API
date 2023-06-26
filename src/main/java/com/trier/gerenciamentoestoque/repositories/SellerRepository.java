package com.trier.gerenciamentoestoque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Client;
import com.trier.gerenciamentoestoque.models.Output;
import com.trier.gerenciamentoestoque.models.Seller;


@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer>{
	
	List<Seller> findByName(String name);
	
	Seller findByRegistration(String registration);
	
}