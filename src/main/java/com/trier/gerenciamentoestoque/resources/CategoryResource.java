package com.trier.gerenciamentoestoque.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

	@Secured({ "ROLE_USER" })
	@GetMapping("/{id}")
	public ResponseEntity<Category> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping
	public ResponseEntity<Category> insert(@RequestBody Category category) {
		return ResponseEntity.ok(service.insert(category));
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/{id}")
	public ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody Category category) {
		category.setId(id);
		return ResponseEntity.ok(service.update(category));
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Secured({ "ROLE_USER" })
	@GetMapping
	public ResponseEntity<List<Category>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/name-starting/{name}")
	public ResponseEntity<List<Category>> findByDescriptionStartingWithIgnoreCase(@PathVariable String name) {
		return ResponseEntity.ok(service.findByDescriptionStartingWithIgnoreCase(name));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/name-order/{name}")
	public ResponseEntity<List<Category>> findByDescriptionContainingOrderByDescriptionDesc(@PathVariable String name) {
		return ResponseEntity.ok(service.findByDescriptionContainingOrderByDescriptionDesc(name));
	}
}
