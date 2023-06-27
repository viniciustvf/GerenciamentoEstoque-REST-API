package com.trier.gerenciamentoestoque.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trier.gerenciamentoestoque.models.Category;
import com.trier.gerenciamentoestoque.repositories.CategoryRepository;
import com.trier.gerenciamentoestoque.services.CategoryService;
import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;



@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	private void validateCategory(Category category) {
		if(category.getDescription() == null) {
			throw new IntegrityViolation("Descrição da categoria não pode ser nula");
		}
	}

	@Override
	public Category findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("A categoria %s não existe".formatted(id)));
	}

	@Override
	public Category insert(Category category) {
		validateCategory(category);
		return repository.save(category);
	}

	@Override
	public List<Category> listAll() {
		List<Category> lista = repository.findAll();
		if ( lista.isEmpty() ) {
			throw new ObjectNotFound("Nenhuma categoria cadastrada");
		}
		return lista;
	}

	@Override
	public Category update(Category category) {
		validateCategory(category);
		return repository.save(category);
	}

	@Override
	public void delete(Integer id) {
		Category category = findById(id);
		repository.delete(category);
	}

	@Override
	public List<Category> findByDescriptionStartingWithIgnoreCase(String description) {
		List<Category> lista = repository.findByDescriptionStartingWithIgnoreCase(description);
		if ( lista.isEmpty() ) {
			throw new ObjectNotFound("Nenhuma categoria cadastrada com a descrição %s".formatted(description));
		}
		return lista;
	}

	@Override
	public List<Category> findByDescriptionOrderByDescriptionDesc(String description) {
		List<Category> lista = repository.findByDescriptionStartingWithIgnoreCase(description);
		if ( lista.isEmpty() ) {
			throw new ObjectNotFound("Nenhuma categoria cadastrada com a descrição %s".formatted(description));
		}
		return lista;
	}
}
