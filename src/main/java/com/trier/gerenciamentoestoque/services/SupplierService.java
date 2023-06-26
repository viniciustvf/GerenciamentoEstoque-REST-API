package com.trier.gerenciamentoestoque.services;

import java.util.List;

import com.trier.gerenciamentoestoque.models.Supplier;

public interface SupplierService {

	Supplier findById(Integer id);
	
	Supplier insert(Supplier supplier);
	
	List<Supplier> listAll();
	
	Supplier update (Supplier supplier);
	
	void delete (Integer id);
	
	List<Supplier> findByName(String name);
	
	Supplier findByCnpj(String cnpj);

}
