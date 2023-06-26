package com.trier.gerenciamentoestoque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trier.gerenciamentoestoque.models.Category;
import com.trier.gerenciamentoestoque.models.Product;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Integer>{
	
	List<Product> findByNameOrderBySizeDesc(String name);

	List<Product> findByPrice(Double price);
	
	List<Product> findByPriceBetween(Double priceI, Double priceF);

	Product findByBarcode(Integer barcode);
	
	List<Product> findByAmount(Integer amount);
	
	List<Product> findByAmountBetween(Integer amountI, Integer amountF);
	
	List<Product> findByCategory(Category category);

}
