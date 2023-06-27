package com.trier.gerenciamentoestoque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

	List<Client> findByNameOrderByNameDesc(String name);
	
	Client findByCpf(String cpf);
	
	List<Client> findByAge(Integer age);
	
	List<Client> findByAgeBetween(Integer ageI, Integer ageF);
	
	Client findByNumber(Long number);
	
}
