package com.trier.gerenciamentoestoque.resources;

import java.util.List;
import java.util.Optional;

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

import com.trier.gerenciamentoestoque.models.Seller;
import com.trier.gerenciamentoestoque.services.SellerService;

@RestController
@RequestMapping("/seller")
public class SellerResource {

	@Autowired
	private SellerService service;

	@Secured({ "ROLE_USER" })
	@GetMapping("/{id}")
	public ResponseEntity<Seller> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping
	public ResponseEntity<Seller> insert(@RequestBody Seller seller) {
		return ResponseEntity.ok(service.insert(seller));
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/{id}")
	public ResponseEntity<Seller> update(@PathVariable Integer id, @RequestBody Seller seller) {
		seller.setId(id);
		return ResponseEntity.ok(service.update(seller));
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Secured({ "ROLE_USER" })
	@GetMapping
	public ResponseEntity<List<Seller>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/name-starts/{name}")
	public ResponseEntity<List<Seller>> findByNameStartingWithIgnoreCaseOrderByNameDesc(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameStartingWithIgnoreCaseOrderByNameDesc(name));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/registration/{registration}")
	public ResponseEntity<Optional<Seller>> findByRegistration(@PathVariable String registration) {
		return ResponseEntity.ok(service.findByRegistration(registration));
	}
}
