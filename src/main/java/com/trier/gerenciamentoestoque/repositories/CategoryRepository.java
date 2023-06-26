package com.trier.gerenciamentoestoque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	List<Category> findByDescriptionOrderBySizeDesc(String description);
	
	List<Category> findByDescriptionStartingWithIgnoreCase(String description);
	
}
