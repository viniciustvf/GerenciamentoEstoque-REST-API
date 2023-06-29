package com.trier.gerenciamentoestoque.services.impl;

import java.util.List;
import java.util.Optional;

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

	private void validateProduct(Product product) {
		if (product.getAmount() == null) {
			throw new IntegrityViolation("A quantidade não pode ser nula");
		}
		if (product.getBarcode() == null) {
			throw new IntegrityViolation("O codigo de barras não pode ser nulo");
		}
		if (product.getCategory() == null) {
			throw new IntegrityViolation("A categoria não pode ser nula");
		}
		if (product.getName() == null) {
			throw new IntegrityViolation("O nome não pode ser nulo");
		}
		if (product.getPrice() == null) {
			throw new IntegrityViolation("O preço não pode ser nulo");
		}
	}

	@Override
	public Product findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("O produto %s não existe".formatted(id)));
	}

	public Product insert(Product product) {
		validateProduct(product);
		return repository.save(product);
	}

	@Override
	public List<Product> listAll() {
		List<Product> lista = repository.findAll();
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhum produto cadastrado");
		}
		return lista;
	}

	@Override
	public Product update(Product product) {
		findById(product.getId());
		validateProduct(product);
		return repository.save(product);
	}

	@Override
	public void delete(Integer id) {
		Product product = findById(id);
		repository.delete(product);
	}

	@Override
	public List<Product> findByNameStartingWithIgnoreCaseOrderByNameDesc(String name) {
		List<Product> lista = repository.findByNameStartingWithIgnoreCaseOrderByNameDesc(name);
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
	public Optional<Product> findByBarcode(Integer barcode) {
		return Optional.ofNullable(repository.findByBarcode(barcode).orElseThrow(
				() -> new ObjectNotFound("Produto não encontrado com código de barras %s".formatted(barcode))));
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
			throw new ObjectNotFound(
					"Nenhum produto encontrado para a categoria %s".formatted(category.getDescription()));
		}
		return lista;
	}

	public Double findTotalValueOfProducts() {
		List<Product> lista = repository.findByAmountBetween(1, repository.findMaxAmount());
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhum produto encontrado");
		}
		return lista.stream().mapToDouble(product -> product.getPrice() * product.getAmount()).sum();
	}
}