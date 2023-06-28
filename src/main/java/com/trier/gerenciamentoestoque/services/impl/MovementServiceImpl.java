package com.trier.gerenciamentoestoque.services.impl;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trier.gerenciamentoestoque.models.Entry;
import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.Output;
import com.trier.gerenciamentoestoque.models.enums.MovementType;
import com.trier.gerenciamentoestoque.repositories.MovementRepository;
import com.trier.gerenciamentoestoque.services.MovementService;
import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;

@Service
public class MovementServiceImpl implements MovementService {

	@Autowired
	private MovementRepository repository;
	
	private void validateMovement(Movement movement) {
		if(movement.getDateTime() == null || movement.getDateTime().isBefore(ZonedDateTime.now())) {
			throw new IntegrityViolation("Data e hora inválidas");
		}
	}
	
	private Movement determineEntryOutput(Movement movement) {
		validateMovement(movement);
		if (movement.getEntry() == null) {
			movement.setMovementType(MovementType.OUTPUT);
		} 
		else if (movement.getOutput() == null) {
			movement.setMovementType(MovementType.ENTRY);
		} else {
			throw new IntegrityViolation("O produto não pode ter uma entrada e uma saída");
		}
		return movement;
	}
	
	@Override
	public Movement findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("O movimento %s não existe".formatted(id)));
	}

	@Override
	public Movement insert(Movement movement) {
		Movement m = determineEntryOutput(movement);
		return repository.save(m);
	}

	@Override
	public List<Movement> listAll() {
		List<Movement> lista = repository.findAll();
		if ( lista.isEmpty() ) { 
			throw new ObjectNotFound("Nenhum movimento cadastrado");
		}
		return lista;
	}

	@Override
	public Movement update(Movement movement) {
		findById(movement.getId());
		Movement m = determineEntryOutput(movement);
		return repository.save(m);
	}

	@Override
	public void delete(Integer id) {
		Movement movement = findById(id);
		repository.delete(movement);
	}


	@Override
	public List<Movement> findByDateTime(ZonedDateTime date) {
		List<Movement> lista = repository.findByDateTime(date);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum movimento encontrado na data %s".formatted(date));
		}
		return lista;
	}

	@Override
	public List<Movement> findByEntry(Entry entry) {
		List<Movement> lista = repository.findByEntry(entry);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum movimento de entrada encontrado para a entrada %s".formatted(entry.getId()));
		}
		return lista;
	}

	@Override
	public List<Movement> findByOutput(Output output) {
		List<Movement> lista = repository.findByOutput(output);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum movimento de saída encontrado para a saída %s".formatted(output.getId()));
		}
		return lista;
	}

}	