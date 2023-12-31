package com.trier.gerenciamentoestoque.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Entry;
import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.Output;


@Repository
public interface MovementRepository extends JpaRepository<Movement, Integer>{
	
	List<Movement> findByDateTime(ZonedDateTime date);
	
	List<Movement> findByDateTimeBetween(ZonedDateTime dateI, ZonedDateTime dateF);
	
	List<Movement> findByEntry(Entry entry);
	
	List<Movement> findByOutput(Output output);
	
}