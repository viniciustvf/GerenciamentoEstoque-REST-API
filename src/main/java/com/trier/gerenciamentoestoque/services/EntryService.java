package com.trier.gerenciamentoestoque.services;

import java.util.List;

import com.trier.gerenciamentoestoque.models.Entry;
import com.trier.gerenciamentoestoque.models.Supplier;

public interface EntryService {

	Entry findById(Integer id);
	
	Entry insert(Entry entry);
	
	List<Entry> listAll();
	
	Entry update (Entry entry);
	
	void delete (Integer id);
	
	List<Entry> findByCodStock(Integer codStock);
	
	List<Entry> findBySupplier(Supplier supplier);
	
}
