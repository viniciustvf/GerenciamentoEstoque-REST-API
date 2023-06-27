package com.trier.gerenciamentoestoque.resources;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trier.gerenciamentoestoque.models.Category;
import com.trier.gerenciamentoestoque.services.CategoryService;


@RestController
@RequestMapping("/category")
public class CategoryResource {

	@Autowired
	private CategoryService service;

	@GetMapping("/{id}")
	public ResponseEntity<Category> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Category> insert(@RequestBody Category category) {
		return ResponseEntity.ok(service.insert(category));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody Category category) {
		category.setId(id);
		return ResponseEntity.ok(service.update(category));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Category>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}

	@GetMapping("/name-starting/{name}")
	public ResponseEntity<List<Category>> findByDescriptionStartingWithIgnoreCase(@PathVariable String name) {
		return ResponseEntity.ok(service.findByDescriptionStartingWithIgnoreCase(name));
	}

	@GetMapping("/name-order/{name}")
	public ResponseEntity<List<Category>> findByDescriptionOrderByDescriptionDesc(@PathVariable String name) {
		return ResponseEntity.ok(service.findByDescriptionOrderByDescriptionDesc(name));
	}
}
