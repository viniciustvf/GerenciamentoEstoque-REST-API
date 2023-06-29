package com.trier.gerenciamentoestoque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Movement;
import com.trier.gerenciamentoestoque.models.Product;
import com.trier.gerenciamentoestoque.models.ProductMovement;

@Repository
public interface  ProductMovementRepository extends JpaRepository<ProductMovement, Integer>{
	
	List<ProductMovement> findByProduct(Product product);

	List<ProductMovement> findByMovement(Movement movement);
	
	List<ProductMovement> findByQuantity(Integer quantity);
	
	List<ProductMovement> findByPrice(Double price);

}
