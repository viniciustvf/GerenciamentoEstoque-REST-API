package com.trier.gerenciamentoestoque.services;

import java.util.List;

import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.Product;
import com.trier.gerenciamentoestoque.models.ProductMovement;

public interface ProductMovementService {

	ProductMovement findById(Integer id);
	
	ProductMovement insert(ProductMovement productMovement);
	
	List<ProductMovement> listAll();
	
	ProductMovement update (ProductMovement productMovement);
	
	void delete (Integer id);
	
	List<ProductMovement> findByProduct(Product product);

	List<ProductMovement> findByMovement(Movement movement);
	
}
