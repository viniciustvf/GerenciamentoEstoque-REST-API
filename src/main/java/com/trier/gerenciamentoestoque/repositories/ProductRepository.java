package com.trier.gerenciamentoestoque.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Category;
import com.trier.gerenciamentoestoque.models.Product;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Integer>{
	
	List<Product> findByNameStartingWithIgnoreCaseOrderByNameDesc(String name);

	List<Product> findByPrice(Double price);
	
	List<Product> findByPriceBetween(Double priceI, Double priceF);

	Optional<Product> findByBarcode(Integer barcode);
	
	List<Product> findByAmount(Integer amount);
	
	List<Product> findByAmountBetween(Integer amountI, Integer amountF);
	
	List<Product> findByCategory(Category category);
	
	@Query(value = "SELECT * FROM product p WHERE p.amount_product = (SELECT MAX(p2.amount_product) FROM product p2) LIMIT 1", nativeQuery = true)
	Integer findMaxAmount();
}
