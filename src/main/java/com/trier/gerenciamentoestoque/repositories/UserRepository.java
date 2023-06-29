package com.trier.gerenciamentoestoque.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByNameStartingWithIgnoreCase(String name);
	
	List<User> findByNameIgnoreCase(String name);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByName(String name);
	
}
