package com.trier.gerenciamentoestoque.services;

import java.util.List;
import java.util.Optional;

import com.trier.gerenciamentoestoque.models.User;

public interface UserService {

	User findById(Integer id);

	User insert(User user);

	List<User> listAll();

	User update(User user);

	void delete(Integer id);

	List<User> findByNameIgnoreCase(String name);

	List<User> findByNameStartingWithIgnoreCase(String name);

	Optional<User> findByEmail(String email);

	Optional<User> findByName(String name);

}
