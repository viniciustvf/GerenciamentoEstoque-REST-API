package com.trier.gerenciamentoestoque.services;

import java.time.ZonedDateTime;
import java.util.List;

import com.trier.gerenciamentoestoque.models.Entry;
import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.Output;

public interface MovementService {

	Movement findById(Integer id);
	
	Movement insert(Movement movement);
	
	List<Movement> listAll();
	
	Movement update (Movement movement);
	
	void delete (Integer id);
	
	List<Movement> findByDateTime(ZonedDateTime date);
	
	List<Movement> findByEntry(Entry entry);
	
	List<Movement> findByOutput(Output output);
	
}
