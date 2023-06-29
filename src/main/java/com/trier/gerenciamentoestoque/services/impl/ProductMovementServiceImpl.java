package com.trier.gerenciamentoestoque.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.Product;
import com.trier.gerenciamentoestoque.models.ProductMovement;
import com.trier.gerenciamentoestoque.models.enums.MovementType;
import com.trier.gerenciamentoestoque.repositories.ProductMovementRepository;
import com.trier.gerenciamentoestoque.services.ProductMovementService;
import com.trier.gerenciamentoestoque.services.exceptions.IntegrityViolation;
import com.trier.gerenciamentoestoque.services.exceptions.ObjectNotFound;

@Service
public class ProductMovementServiceImpl implements ProductMovementService {

	@Autowired
	private ProductMovementRepository repository;
	
	private void validateProductMovement(ProductMovement productMovement) {
		if(productMovement.getMovement() == null) {
			throw new IntegrityViolation("Movimento não pode ser nulo");
		} 
		if(productMovement.getProduct() == null) {
			throw new IntegrityViolation("Produto não pode ser nulo");
		}
		if(productMovement.getQuantity() == null || productMovement.getQuantity() > 100) {
			throw new IntegrityViolation("Quantidade não pode ser nula e maior que 100"); 
		}
	}

	private ProductMovement determineEntryOutput(ProductMovement productMovement) {
		validateProductMovement(productMovement);
		if(productMovement.getMovement().getMovementType().equals(MovementType.ENTRY)) {
			productMovement.getProduct().setAmount(productMovement.getProduct().getAmount() + productMovement.getQuantity());
			productMovement.setPrice(productMovement.getProduct().getPrice());
		} else {
			productMovement.getProduct().setAmount(productMovement.getProduct().getAmount() - productMovement.getQuantity());
			productMovement.setPrice(productMovement.getProduct().getPrice());
		}
		return productMovement;
	}
	
	
	@Override
	public ProductMovement findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("O produto movimento %s não existe".formatted(id)));
	}

	@Override
	public ProductMovement insert(ProductMovement productMovement) {
		determineEntryOutput(productMovement);
		return repository.save(productMovement);
	}

	@Override
	public List<ProductMovement> listAll() {
		List<ProductMovement> lista = repository.findAll();
		if ( lista.isEmpty() ) {
			throw new ObjectNotFound("Nenhum produto movimento cadastrado");
		}
		return lista;
	}

	@Override
	public ProductMovement update(ProductMovement productMovement) {
		findById(productMovement.getId());
		determineEntryOutput(productMovement);
		return repository.save(productMovement);
	}

	@Override
	public void delete(Integer id) {
		ProductMovement productMovement = findById(id);
		repository.delete(productMovement);
	}

	@Override
	public List<ProductMovement> findByProduct(Product product) {
		List<ProductMovement> lista = repository.findByProduct(product);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum produto movimento encontrado para o produto %s".formatted(product.getName()));
		}
		return lista;
	}

	@Override
	public List<ProductMovement> findByMovement(Movement movement) {
		List<ProductMovement> lista = repository.findByMovement(movement);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum movimento com id %s encontrado".formatted(movement.getId()));
		}
		return lista;
	}

	@Override
	public List<ProductMovement> findByQuantity(Integer quantity) {
		List<ProductMovement> lista = repository.findByQuantity(quantity);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum produto movimento encontrado com a quantidade de %s produtos".formatted(quantity));
		}
		return lista;
	}
	
	@Override
	public List<ProductMovement> findByPrice(Double price) {
		List<ProductMovement> lista = repository.findByPrice(price);
		if (lista.isEmpty()) {	
			throw new ObjectNotFound("Nenhum produto movimento encontrado com o preço de %s".formatted(price));
		}
		return lista;
	}
	
	
}	