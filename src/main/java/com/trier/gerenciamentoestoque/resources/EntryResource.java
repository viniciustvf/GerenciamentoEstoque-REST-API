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

import com.trier.gerenciamentoestoque.models.Entry;
import com.trier.gerenciamentoestoque.models.Supplier;
import com.trier.gerenciamentoestoque.services.EntryService;
import com.trier.gerenciamentoestoque.services.SupplierService;


@RestController
@RequestMapping("/entry")
public class EntryResource {

	@Autowired
	private EntryService service;
	
	@Autowired
	private SupplierService supplierService;

	@GetMapping("/{id}")
	public ResponseEntity<Entry> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Entry> insert(@RequestBody Entry entry) {
		return ResponseEntity.ok(service.insert(entry));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Entry> update(@PathVariable Integer id, @RequestBody Entry entry) {
		entry.setId(id);
		return ResponseEntity.ok(service.update(entry));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Entry>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}

	@GetMapping("/codstock/{cod}")
	public ResponseEntity<List<Entry>> findByCodStock(@PathVariable Integer cod) {
		return ResponseEntity.ok(service.findByCodStock(cod));
	}
	
	@GetMapping("/supplier/{supplierId}")
	public ResponseEntity<List<Entry>> findBySupplier(@PathVariable Supplier supplierId) {
		return ResponseEntity.ok(service.findBySupplier(supplierService.findById(supplierId.getId())));
	}
}
