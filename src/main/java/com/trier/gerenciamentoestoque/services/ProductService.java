package com.trier.gerenciamentoestoque.services;

import java.util.List;
import java.util.Optional;

import com.trier.gerenciamentoestoque.models.Category;
import com.trier.gerenciamentoestoque.models.Product;

public interface ProductService {

	Product findById(Integer id);

	Product insert(Product product);

	List<Product> listAll();

	Product update(Product product);

	void delete(Integer id);

	List<Product> findByNameStartingWithIgnoreCaseOrderByNameDesc(String name);

	List<Product> findByPrice(Double price);

	List<Product> findByPriceBetween(Double priceI, Double priceF);

	Optional<Product> findByBarcode(Integer barcode);

	List<Product> findByAmount(Integer amount);

	List<Product> findByAmountBetween(Integer amountI, Integer amountF);

	List<Product> findByCategory(Category category);

	Double findTotalValueOfProducts();

}
