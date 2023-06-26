package com.trier.gerenciamentoestoque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Client;
import com.trier.gerenciamentoestoque.models.Output;
import com.trier.gerenciamentoestoque.models.Seller;
import com.trier.gerenciamentoestoque.models.Supplier;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>{
	
	List<Supplier> findByName(String name);
	
	Supplier findByCnpj(String cnpj);
	
}