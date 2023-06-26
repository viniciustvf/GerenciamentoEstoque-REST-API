package com.trier.gerenciamentoestoque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.qos.logback.core.net.server.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

	List<Client> findByNameOrderBySizeDesc(String name);
	
	Client findByCpf(String cpf);
	
	List<Client> findByAge(Integer age);
	
	List<Client> findByAgeBetween(Integer ageI, Integer ageF);
	
	Client findByNumber(String number);
	
}
