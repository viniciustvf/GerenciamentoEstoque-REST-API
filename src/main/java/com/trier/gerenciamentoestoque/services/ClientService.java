package com.trier.gerenciamentoestoque.services;

import java.util.List;

import com.trier.gerenciamentoestoque.models.Client;

public interface ClientService {

	Client findById(Integer id);
	
	Client insert(Client client);
	
	List<Client> listAll();
	
	Client update (Client client);
	
	void delete (Integer id);
	
	List<Client> findByNameOrderBySizeDesc(String name);
	
	Client findByCpf(String cpf);
	
	List<Client> findByAge(Integer age);
	
	List<Client> findByAgeBetween(Integer ageI, Integer ageF);
	
	Client findByNumber(String number);
	
}
