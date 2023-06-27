package com.trier.gerenciamentoestoque.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trier.gerenciamentoestoque.models.Category;
import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.Product;
import com.trier.gerenciamentoestoque.models.enums.MovementType;
import com.trier.gerenciamentoestoque.repositories.ProductRepository;
import com.trier.gerenciamentoestoque.services.ProductService;
import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;
	
	private void validateProduct(Product productMovement) {
		if(productMovement.getAmount() == null) {
			throw new IntegrityViolation("A quantidade não pode ser nula");
		}
		if(productMovement.getBarcode() == null) {
			throw new IntegrityViolation("O codigo de barras não pode ser nulo");
		}
		if(productMovement.getCategory() == null) {
			throw new IntegrityViolation("A categoria não pode ser nula");
		}
		if(productMovement.getName() == null) {
			throw new IntegrityViolation("O nome não pode ser nulo");
		}
		if(productMovement.getPrice() == null) {
			throw new IntegrityViolation("O preço não pode ser nulo");
		}
	}

	@Override
	public Product findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("O produto %s não existe".formatted(id)));
	}

	@Override
	public Product insert(Product productMovement) {
		validateProduct(productMovement);
		return repository.save(productMovement);
	}

	@Override
	public List<Product> listAll() {
		List<Product> lista = repository.findAll();
		if ( lista.isEmpty() ) {
			throw new ObjectNotFound("Nenhuma produto movimento cadastrado");
		}
		return lista;
	}

	@Override
	public Product update(Product productMovement) {
		validateProduct(productMovement);
		return repository.save(productMovement);
	}

	@Override
	public void delete(Integer id) {
		Product productMovement = findById(id);
		repository.delete(productMovement);
	}

	@Override
	public List<Product> findByNameOrderByNameDesc(String name) {
		List<Product> lista = repository.findByNameOrderByNameDesc(name);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum produto encontrado com o nome %s".formatted(name));
		}
		return lista;
	}

	@Override
	public List<Product> findByPrice(Double price) {
		List<Product> lista = repository.findByPrice(price);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum produto encontrado com o preço %s".formatted(price));
		}
		return lista;
	}

	@Override
	public List<Product> findByPriceBetween(Double priceI, Double priceF) {
		List<Product> lista = repository.findByPriceBetween(priceI, priceF);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum produto encontrado entre R$%s e R$%s".formatted(priceI, priceF));
		}
		return lista;
	}

	@Override
	public Product findByBarcode(Integer barcode) {
		return repository.findByBarcode(barcode);
	}

	@Override
	public List<Product> findByAmount(Integer amount) {
		List<Product> lista = repository.findByAmount(amount);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum produto encontrado com estoque de %s".formatted(amount));
		}
		return lista;
	}

	@Override
	public List<Product> findByAmountBetween(Integer amountI, Integer amountF) {
		List<Product> lista = repository.findByAmountBetween(amountI, amountF);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum produto encontrado com estoque entre %s e %s".formatted(amountI, amountF));
		}
		return lista;
	}

	@Override
	public List<Product> findByCategory(Category category) {
		List<Product> lista = repository.findByCategory(category);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum produto encontrado para a categoria %s".formatted(category));
		}
		return lista;
	}
}	