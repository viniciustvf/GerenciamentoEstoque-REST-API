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

import com.trier.gerenciamentoestoque.models.Client;
import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.Product;
import com.trier.gerenciamentoestoque.models.ProductMovement;
import com.trier.gerenciamentoestoque.models.dto.ProductMovementDTO;
import com.trier.gerenciamentoestoque.services.MovementService;
import com.trier.gerenciamentoestoque.services.ProductMovementService;
import com.trier.gerenciamentoestoque.services.ProductService;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;


@RestController
@RequestMapping("/ProductMovement")
public class ProductMovementResource {

	@Autowired
	private ProductMovementService service;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MovementService movementService;

	@GetMapping("/{id}")
	public ResponseEntity<ProductMovementDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@PostMapping
	public ResponseEntity<ProductMovementDTO> insert(@RequestBody ProductMovementDTO ProductMovement) {
		ProductMovement pm = new ProductMovement(ProductMovement, productService.findById(ProductMovement.getProductId()), movementService.findById(ProductMovement.getMovementId()));
		return ResponseEntity.ok(service.insert(pm).toDTO());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductMovementDTO> update(@PathVariable Integer id, @RequestBody ProductMovementDTO ProductMovement) {
		ProductMovement pm = new ProductMovement(ProductMovement, productService.findById(ProductMovement.getProductId()), movementService.findById(ProductMovement.getMovementId()));
		pm.setId(id);
		return ResponseEntity.ok(service.update(pm).toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<ProductMovementDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((ProductMovement) -> ProductMovement.toDTO()).toList());
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<ProductMovementDTO>> findByProduct(@PathVariable Integer productId) {
		List<ProductMovement> lista = service.findByProduct(productService.findById(productId));
		return ResponseEntity.ok(lista.stream().map((ProductMovement) -> ProductMovement.toDTO()).toList());		
	}
	
	@GetMapping("/movement/{movementId}")
	public ResponseEntity<List<ProductMovementDTO>> findByMovement(@PathVariable Integer movementId) {
		List<ProductMovement> lista = service.findByMovement(movementService.findById(movementId));
		return ResponseEntity.ok(lista.stream().map((ProductMovement) -> ProductMovement.toDTO()).toList());		
	}
	
	@GetMapping("/quantity/{quantity}")
	public ResponseEntity<List<ProductMovementDTO>> findByQuantity(@PathVariable Integer quantity) {
		List<ProductMovement> lista = service.findByQuantity(quantity);
		return ResponseEntity.ok(lista.stream().map((ProductMovement) -> ProductMovement.toDTO()).toList());	
	}
}
