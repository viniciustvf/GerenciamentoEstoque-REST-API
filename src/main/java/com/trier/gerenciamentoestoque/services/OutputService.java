package com.trier.gerenciamentoestoque.services;

import java.util.List;

import com.trier.gerenciamentoestoque.models.Client;
import com.trier.gerenciamentoestoque.models.Output;
import com.trier.gerenciamentoestoque.models.Seller;

public interface OutputService {

	Output findById(Integer id);
	
	Output insert(Output output);
	
	List<Output> listAll();
	
	Output update (Output output);
	
	void delete (Integer id);
	
	List<Output> findByClient(Client client);
	
	List<Output> findBySeller(Seller seller);
	
}
