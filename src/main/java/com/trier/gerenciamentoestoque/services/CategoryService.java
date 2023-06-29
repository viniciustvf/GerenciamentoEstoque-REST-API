package com.trier.gerenciamentoestoque.services;

import java.util.List;

import com.trier.gerenciamentoestoque.models.Category;

public interface CategoryService {

	Category findById(Integer id);

	Category insert(Category category);

	List<Category> listAll();

	Category update(Category category);

	void delete(Integer id);

	List<Category> findByDescriptionContainingOrderByDescriptionDesc(String description);

	List<Category> findByDescriptionStartingWithIgnoreCase(String description);

}
