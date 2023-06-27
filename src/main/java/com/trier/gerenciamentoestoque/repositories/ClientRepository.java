package com.trier.gerenciamentoestoque.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

	List<Client> findByNameOrderByNameDesc(String name);
	
	Optional<Client> findByCpf(String cpf);
	
	List<Client> findByAge(Integer age);
	
	List<Client> findByAgeBetween(Integer ageI, Integer ageF);
	
	Optional<Client> findByNumber(Long number);
	
}
