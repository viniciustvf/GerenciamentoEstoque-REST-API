package com.trier.gerenciamentoestoque.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trier.gerenciamentoestoque.models.Entry;
import com.trier.gerenciamentoestoque.models.Supplier;
import com.trier.gerenciamentoestoque.repositories.EntryRepository;
import com.trier.gerenciamentoestoque.services.EntryService;
import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;

@Service
public class EntryServiceImpl implements EntryService {

	@Autowired
	private EntryRepository repository;
	
	private void validateEntry(Entry entry) {
		if(entry.getCodStock() == null) {
			throw new IntegrityViolation("Código do estoque não pode ser nulo");
		}
		if(entry.getSupplier() == null) {
			throw new IntegrityViolation("Fornecedor não pode ser nulo");
		}
	}

	@Override
	public Entry findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("A entrada %s não existe".formatted(id)));
	}

	@Override
	public Entry insert(Entry entry) {
		validateEntry(entry);
		return repository.save(entry); 
	}

	@Override
	public List<Entry> listAll() {
		List<Entry> lista = repository.findAll();
		if ( lista.isEmpty() ) {
			throw new ObjectNotFound("Nenhuma entrada cadastrada");
		}
		return lista;
	}

	@Override
	public Entry update(Entry entry) {
		findById(entry.getId());
		validateEntry(entry);
		return repository.save(entry);
	}

	@Override
	public void delete(Integer id) {
		Entry entry = findById(id);
		repository.delete(entry);
	}

	@Override
	public List<Entry> findByCodStock(Integer codStock) {
		List<Entry> lista = repository.findByCodStock(codStock);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum estoque encontrado com código %s".formatted(codStock));
		}
		return lista;
	}

	@Override
	public List<Entry> findBySupplier(Supplier supplier) {
		List<Entry> lista = repository.findBySupplier(supplier);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum fornecedor %s encontrado".formatted(supplier.getId()));
		}
		return lista;
	}
}	