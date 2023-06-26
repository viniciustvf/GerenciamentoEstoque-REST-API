package com.trier.gerenciamentoestoque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Entry;
import com.trier.gerenciamentoestoque.models.Supplier;


@Repository
public interface EntryRepository extends JpaRepository<Entry, Integer>{
	
	List<Entry> findByCodStock(Integer codStock);
	
	List<Entry> findBySupplier(Supplier supplier);
	
}