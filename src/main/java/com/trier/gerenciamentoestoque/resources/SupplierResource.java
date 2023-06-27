package com.trier.gerenciamentoestoque.resources;

import java.util.List;
import java.util.Optional;

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

import com.trier.gerenciamentoestoque.models.Supplier;
import com.trier.gerenciamentoestoque.services.SupplierService;


@RestController
@RequestMapping("/supplier")
public class SupplierResource {

	@Autowired
	private SupplierService service;

	@GetMapping("/{id}")
	public ResponseEntity<Supplier> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Supplier> insert(@RequestBody Supplier supplier) {
		return ResponseEntity.ok(service.insert(supplier));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Supplier> update(@PathVariable Integer id, @RequestBody Supplier supplier) {
		supplier.setId(id);
		return ResponseEntity.ok(service.update(supplier));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Supplier>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Supplier>> findByName(@PathVariable String name) {
		return ResponseEntity.ok(service.findByName(name));
	}

	@GetMapping("/registration/{registration}")
	public ResponseEntity<Optional<Supplier>> findByCnpj(@PathVariable String cnpj) {
		return ResponseEntity.ok(service.findByCnpj(cnpj));
	}
}
