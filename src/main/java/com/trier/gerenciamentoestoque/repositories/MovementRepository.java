package com.trier.gerenciamentoestoque.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Entry;
import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.Output;
import com.trier.gerenciamentoestoque.models.Supplier;


@Repository
public interface MovementRepository extends JpaRepository<Movement, Integer>{
	
	List<Movement> findByDate(ZonedDateTime date);
	
	List<Movement> findByQuantity(Integer quantity);
	
	List<Movement> findByEntry(Entry entry);
	
	List<Movement> findByOutput(Output output);
	
}