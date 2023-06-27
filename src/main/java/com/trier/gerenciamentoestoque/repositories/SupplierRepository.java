package com.trier.gerenciamentoestoque.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Supplier;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>{
	
	List<Supplier> findByName(String name);
	
	Optional<Supplier> findByCnpj(String cnpj);
	
}