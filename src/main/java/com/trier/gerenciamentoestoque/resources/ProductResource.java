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

import com.trier.gerenciamentoestoque.models.Product;
import com.trier.gerenciamentoestoque.models.dto.ProductDTO;
import com.trier.gerenciamentoestoque.services.CategoryService;
import com.trier.gerenciamentoestoque.services.ProductService;


@RestController
@RequestMapping("/Product")
public class ProductResource {

	@Autowired
	private ProductService service;
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@PostMapping
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO Product) {
		Product p = new Product(Product, categoryService.findById(Product.getCategoryId()));
		return ResponseEntity.ok(service.insert(p).toDTO());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Integer id, @RequestBody ProductDTO Product) {
		Product p = new Product(Product, categoryService.findById(Product.getCategoryId()));
		p.setId(id);
		return ResponseEntity.ok(service.update(p).toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((Product) -> Product.toDTO()).toList());
	}
	
	@GetMapping("/name-starts/{name}")
	public ResponseEntity<List<ProductDTO>> findByNameStartingWithIgnoreCaseOrderByNameDesc(@PathVariable String name) {
		List<Product> lista = service.findByNameStartingWithIgnoreCaseOrderByNameDesc(name);
		return ResponseEntity.ok(lista.stream().map((Product) -> Product.toDTO()).toList());		
	}
	
	@GetMapping("/price/{price}")
	public ResponseEntity<List<ProductDTO>> findByPrice(@PathVariable Double price) {
		List<Product> lista = service.findByPrice(price);
		return ResponseEntity.ok(lista.stream().map((Product) -> Product.toDTO()).toList());		
	}
	
	@GetMapping("/price-between/{priceI}/{priceF}")
	public ResponseEntity<List<ProductDTO>> findByPriceBetween(@PathVariable Double priceI, @PathVariable Double priceF) {
		List<Product> lista = service.findByPriceBetween(priceI, priceF);
		return ResponseEntity.ok(lista.stream().map((Product) -> Product.toDTO()).toList());		
	}
	
	@GetMapping("/barcode/{barcode}")
	public ResponseEntity<List<ProductDTO>> findByBarcode(@PathVariable Integer barcode) {
		Optional<Product> product = service.findByBarcode(barcode);
		return ResponseEntity.ok(product.stream().map((Product) -> Product.toDTO()).toList());		
	}
	
	@GetMapping("/amount/{amount}")
	public ResponseEntity<List<ProductDTO>> findByAmount(@PathVariable Integer amount) {
		List<Product> lista = service.findByAmount(amount);
		return ResponseEntity.ok(lista.stream().map((Product) -> Product.toDTO()).toList());		
	}
	
	@GetMapping("/amount-between/{amountI}/{amountF}")
	public ResponseEntity<List<ProductDTO>> findByAmountBetween(@PathVariable Double amountI, @PathVariable Double amountF) {
		List<Product> lista = service.findByPriceBetween(amountI, amountF);
		return ResponseEntity.ok(lista.stream().map((Product) -> Product.toDTO()).toList());		
	}

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<ProductDTO>> findByCategory(@PathVariable Integer categoryId) {
		List<Product> lista = service.findByCategory(categoryService.findById(categoryId));
		return ResponseEntity.ok(lista.stream().map((Product) -> Product.toDTO()).toList());		
	}
}
