package com.trier.gerenciamentoestoque.services;

import java.util.List;
import java.util.Optional;

import com.trier.gerenciamentoestoque.models.Client;

public interface ClientService {

	Client findById(Integer id);
	
	Client insert(Client client);
	
	List<Client> listAll();
	
	Client update (Client client);
	
	void delete (Integer id);
	
	List<Client> findByNameOrderByNameDesc(String name);
	
	Optional<Client> findByCpf(String cpf);
	
	List<Client> findByAge(Integer age);
	
	List<Client> findByAgeBetween(Integer ageI, Integer ageF);
	
	Optional<Client> findByNumber(Long number);
	
}
